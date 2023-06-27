<template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="水平：" prop="gradeLevel" required>
        <el-select v-model="form.gradeLevel" placeholder="水平" @change="levelChange">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Subject：" prop="subjectId" required>
        <el-select v-model="form.subjectId" placeholder="Subject">
          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id" :label="item.name+' ( '+item.levelName+' )'"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="EN：" prop="title" required>
        <el-input v-model="form.title" @focus="inputClick(form,'title')" />
      </el-form-item>
      <el-form-item label="CN：" prop="correct" required>
        <el-input v-model="form.correct" @focus="inputClick(form,'correct')" />
      </el-form-item>
      <el-form-item label="解析：" prop="analyze" required>
        <el-input v-model="form.analyze" @focus="inputClick(form,'analyze')" />
      </el-form-item>
      <el-form-item label="subtitle" >
        <el-input type="textarea" rows=10 @select="onselect" @click="onselect" @mouseup.stop="onselect($event)" v-model="form.subtitle" />
      </el-form-item>
      <el-form-item label="code：" >
        <el-input type="textarea" rows=10 v-model="form.json" :value="tomato" />
      </el-form-item>
      <el-form-item label="SRC：" prop="src" required>
        <el-input v-model="form.src" :value="tomato.src" />
        <audio :src="tomato.src" controls ref="audio" @play='audioPlaying' style='width: 100%;display: none'></audio>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="tomatoRestart">|-</el-button>
        <el-button type="primary" @click="tomatoAllstop">Allstop</el-button>
        <el-button type="primary" @click="tomatoPause">暂停</el-button>
        <el-button type="primary" @click="tomatoResume">继续</el-button>
      </el-form-item>
      <el-form-item>
        {{currentChunk}}
        <el-button type="primary" @click="chunkCreate">Append</el-button>
        <el-button type="primary" @click="chunkPrev">Prev</el-button>
        <el-button type="primary" @click="chunkNext">Next</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="setPoint">One stop</el-button>
        <el-button type="primary" @click="setStart">设置起点</el-button>
        <el-button type="primary" @click="setEnd">设置止点</el-button>
        <el-input-number v-model="form.now" :precision="2" :step="0.1" :max="100"></el-input-number>
        {{form.current}}
        <el-button type="primary" @click="tryPlay">test</el-button>
        <ul>
          <li @click='chunkHandler(index)' v-for='(item,index) in tomato.chunks' :key="item.id" :class='{active:index===currentChunk}'>
            {{item.id}}. {{ item.start }}-{{ item.end }} {{ item.text }} <el-button type="primary" @click="removeChunk(index)">remove</el-button>
            <el-button type="primary" @click="tryPlayChunk(index)">test</el-button>
          </li>
        </ul>
      </el-form-item>
      <el-form-item label="Chunk：" prop="start">
        <el-col :span="8">
          <el-input-number :value="form.start" v-model="form.start" :precision="2" :step="0.1" :max="100"></el-input-number>
        </el-col>
        <el-col :span="8">
          <el-input-number :value="form.end" v-model="form.end" :precision="2" :step="0.1" :max="100"></el-input-number>
        </el-col>
      </el-form-item>
      <el-form-item label="分数：" prop="score" required>
        <el-input-number v-model="form.score" :precision="1" :step="1" :max="100"></el-input-number>
      </el-form-item>
      <el-form-item label="难度：" required>
        <el-rate v-model="form.difficult" class="question-item-rate"></el-rate>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交6</el-button>
        <el-button @click="resetForm">重置</el-button>
        <el-button type="success" @click="showQuestion">预览</el-button>
      </el-form-item>
    </el-form>
    <el-dialog :visible.sync="richEditor.dialogVisible" append-to-body :close-on-click-modal="false" style="width: 100%;height: 100%" :show-close="false" center>
      <Ueditor @ready="editorReady" />
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editorConfirm">确 定</el-button>
        <el-button @click="richEditor.dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="questionShow.dialog" style="width: 100%;height: 100%">
      <QuestionShow :qType="questionShow.qType" :question="questionShow.question" :qLoading="questionShow.loading" />
    </el-dialog>
  </div>
</template>
<script>
import QuestionShow from '../components/Show'
import Ueditor from '@/components/Ueditor'
import { mapGetters, mapState, mapActions } from 'vuex'
import questionApi from '@/api/question'


export default {
  components: {
    Ueditor,
    QuestionShow
  },
  data() {
    return {
      // musicData: [
      //   {
      //     id: 1,
      //     name: 'NCE2-1',
      //     author: 'NCE',
      //     src: 'https://t.datajiang.com/nce2-1.mp3',
      //     start: 0,
      //     end: 0
      //   },
      // {
      //   id: 2,
      //   name: 'NCE2-2',
      //   author: 'NCE',
      //   src: 'https://t.datajiang.com/nce2-2.mp3',
      //   start: 0,
      //   end: 0
      // }
      // ],
      // currentIndex: 0,
      allstop: false,
      currentChunk: 0,
      tomato: {
        src: 'https://t.datajiang.com/nce2-1.mp3',
        poster: '/images/video/logo.png',
        name: '',
        author: 'N C 2-1',
        uri: 'https://t.datajiang.com/nce2-1.mp3',
        title: "NCE 2 lesson 1 A private conversation",
        subtitle: '',
        chunks: [
          // {
          //   "id": 1, 
          //   "text":"passage one a private conversation.",
          //   "textcn":"第一课 私人谈话",
          //   "start":1.62,
          //   "end":4.8,
          // }
        ]
      },
      form: {
        id: null,
        questionType: 6,
        gradeLevel: null,
        subjectId: null,
        title: '',
        subtitle: '',
        json: '',
        items: [],
        analyze: '',
        correct: '',
        score: '',
        difficult: 0,
        start: 0,
        end: 0
      },
      subjectFilter: null,
      formLoading: false,
      rules: {
        gradeLevel: [
          { required: true, message: '请选择水平', trigger: 'change' }
        ],
        subjectId: [
          { required: true, message: '请选择Subject', trigger: 'change' }
        ],
        title: [
          { required: true, message: '请输入题干', trigger: 'blur' }
        ],
        correct: [
          { required: true, message: '请输入答案', trigger: 'blur' }
        ],
        analyze: [
          { required: true, message: '请输入解析', trigger: 'blur' }
        ],
        score: [
          { required: true, message: '请输入分数', trigger: 'blur' }
        ]
      },
      richEditor: {
        dialogVisible: false,
        object: null,
        parameterName: '',
        instance: null
      },
      questionShow: {
        qType: 0,
        dialog: false,
        question: null,
        loading: false
      }
    }
  },
  created() {
    let id = this.$route.query.id

    let _this = this
    this.initSubject(function() {
      _this.subjectFilter = _this.subjects
    })
    if (id && parseInt(id) !== 0) {
      _this.formLoading = true
      questionApi.select(id).then(re => {
        _this.form = re.response
        _this.formLoading = false
      })
    }
  },
  methods: {
    aaaaaa() {
      console.log('aaa...')
    },
    onselect(e) {
      console.log('onselect...')
      let start=e.target.selectionStart
      let end=e.target.selectionEnd
      let value=e.target.value
      console.log( start, end , value)

      let selectVal = ""
      if(value&&end!=0){
        selectVal=value.slice(start,end)
      }
      console.log(selectVal)
    },
    audioPlaying() {

      if (this.allstop) {
        return
      }

      // if (this.playing )
      {
        setTimeout(() => {
          this.audioPlaying()
        }, 100)
      }

      console.log('this...', this)

      let audio = this.$refs.audio
      console.log(typeof(audio))
      if (audio == null || typeof(audio) == 'undefined' || typeof(audio) == undefined || audio == undefined || audio == 'undefined') {
        console.log('audio error!', audio)
        return
      }
      if (audio == null || typeof(audio.readyState) == 'undefined' || audio.readyState == 'undefined' || audio.readyState < 4) {
        console.log('readyState error! continue')
        // return
      }

      // this.form.now1 = audio.currentTime
      if (audio == null || typeof(audio.currentTime) == 'undefined' || audio.currentTime == 'undefined') {
        console.log(`currentTime error!`)
      } else {
        this.form.current = audio.currentTime
        console.log(`currentTime: `, audio.currentTime, `duration: `, audio.duration)

        let chunk = this.tomato.chunks[this.currentChunk]
        // console.log(`start: `, t.start ,`end: `, t.end)
        if (this.trymode && chunk.end > 0 && audio.currentTime > chunk.end) {
          console.log('end to pause')
          audio.pause()
        }
      }
    },
    tomatoSeeking() {
      console.log('seeking...')
      let audio = this.$refs.audio
      console.log(audio.currentTime)
    },
    tomatoSeeked() {
      console.log('seeked')
      let audio = this.$refs.audio
      console.log(audio.currentTime)
    },
    chunkHandler(i) {
      this.currentChunk = i
    },
    //播放下一首
    nextHanlder() {
      this.currentChunk++
    },
    chunkCreate() {
      console.log('this.tomato=', this.tomato)
      let t = this.tomato

      console.log('t.currentChunk=', this.currentChunk)
      console.log('t.chunks.length=', t.chunks.length)

      let c = {
        // "id": 2, 
        "text": "private conversation.",
        "textcn": "",
        "start": 1.62,
        "end": 4.8,
      }
      c.id = t.chunks.length + 1
      console.log('new chunk', c.id)
      c.start = 0
      c.end = 0

      t.chunks.push(c)
      // this.tomato.chunks=t.chunks
    },
    chunkPrev() {
      this.currentChunk--
    },
    chunkNext() {
      this.currentChunk++
    },
    tomatoPause() {

      let audio = this.$refs.audio
      audio.pause()
      // console.log(audio.seeking)
      console.log(`currentTime: `, audio.currentTime, `duration: `, audio.duration)

      const length = audio.played.length
      console.log(`length: ${length}`)

      for (var i = 0; i < length; i++) {
        var start = audio.played.start(i)
        var end = audio.played.end(i)

        console.log(`index: ${i}, start: ${start}, end: ${end}, durations: ${end - start}s`)
      }
    },
    tomatoRestart() {
      console.log('restart')
      this.trymode = false
      // let audio = document.querySelector('audio')
      let audio = this.$refs.audio
      audio.currentTime = 0
      audio.play()
    },
    tomatoAllstop() {
      console.log('allstop!')
      this.allstop = true
      let audio = this.$refs.audio
      audio.pause()
    },
    tomatoResume() {
      console.log('resume...')

      this.allstop = false
      this.trymode = false
      // let audio = document.querySelector('audio')
      let audio = this.$refs.audio
      audio.play()
      console.log(audio.seeking)
    },
    tryPlayChunk(index) {
      console.log('tryPlayChunk ', index, this.currentChunk)
      this.trymode = true

      let audio = this.$refs.audio

      console.log(`currentTime: `, audio.currentTime, `duration: `, audio.duration)

      let chunk = this.tomato.chunks[index]
      console.log(`start: `, chunk.start, `end: `, chunk.end)
      this.$refs.audio.currentTime = chunk.start
      this.$refs.audio.play()

    },
    tryPlay() {
      console.log('tryPlay, currentChunk: ', this.currentChunk)
      console.log('tomato.chunks: ', this.tomato.chunks)

      this.trymode = true

      let audio = this.$refs.audio

      console.log(`currentTime: `, audio.currentTime, `duration: `, audio.duration)

      let chunk = this.tomato.chunks[this.currentChunk]
      console.log(`start: `, chunk.start, `end: `, chunk.end)
      this.$refs.audio.currentTime = chunk.start
      this.$refs.audio.play()

      const length = audio.played.length
      console.log(`length: ${length}`)


      for (var i = 0; i < length; i++) {
        var start = audio.played.start(i)
        var end = audio.played.end(i)

        console.log(`index: ${i}, start: ${start}, end: ${end}, durations: ${end - start}s`)
      }
    },
    setPoint() {

      // all step from 0 chunk to all chunks
      let audio = this.$refs.audio
      console.log(`currentTime: `, audio.currentTime, `duration: `, audio.duration)

      if (this.tomato.chunks.length < this.currentChunk + 1) {
        this.chunkCreate()
      }

      let chunk = this.tomato.chunks[this.currentChunk]
      console.log('chunk:', chunk)

      chunk.end = audio.currentTime
      console.log('set current end...', chunk.end)

      this.currentChunk++
      if (this.tomato.chunks.length < this.currentChunk + 1) {
        this.chunkCreate()
      }

      chunk = this.tomato.chunks[this.currentChunk]
      console.log('set to next ', chunk.id)
      chunk.start = audio.currentTime
      chunk.end = audio.duration

      this.form.start = chunk.start
      this.form.end = chunk.end
      this.form.json = JSON.stringify(this.tomato)
    },
    removeChunk(i) {
      this.tomato.chunks.splice(i, 1)
      this.currentChunk = this.tomato.chunks.length
    },
    removeAllChunks() {
      this.tomato.chunks = []
      this.currentChunk = this.tomato.chunks.length
    },
    setStart() {
      let audio = this.$refs.audio
      console.log(`currentTime: `, audio.currentTime)

      let chunk = this.tomato.chunks[this.currentChunk]
      console.log('chunk:', chunk, chunk.start)

      chunk.start = audio.currentTime
      console.log('setStart...', chunk.start)

      this.form.start = chunk.start
    },
    setEnd() {
      let audio = this.$refs.audio
      console.log(`currentTime: `, audio.currentTime)
      let chunk = this.tomato.chunks[this.currentChunk]
      chunk.end = audio.currentTime
      console.log('setEnd...', chunk.end)
      this.form.end = chunk.end
    },
    editorReady(instance) {
      this.richEditor.instance = instance
      let currentContent = this.richEditor.object[this.richEditor.parameterName]
      this.richEditor.instance.setContent(currentContent)
      // 光标定位到Ueditor
      this.richEditor.instance.focus(true)
    },
    inputClick(object, parameterName) {
      this.richEditor.object = object
      this.richEditor.parameterName = parameterName
      this.richEditor.dialogVisible = true
    },
    editorConfirm() {
      let content = this.richEditor.instance.getContent()
      this.richEditor.object[this.richEditor.parameterName] = content
      this.richEditor.dialogVisible = false
    },
    submitForm() {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          questionApi.edit(this.form).then(re => {
            if (re.code === 1) {
              _this.$message.success(re.message)
              _this.delCurrentView(_this).then(() => {
                _this.$router.push('/exam/question/list')
              })
            } else {
              _this.$message.error(re.message)
              this.formLoading = false
            }
          }).catch(e => {
            this.formLoading = false
          })
        } else {
          return false
        }
      })
    },
    resetForm() {
      let lastId = this.form.id
      this.$refs['form'].resetFields()
      this.form = {
        id: null,
        questionType: 6,
        gradeLevel: null,
        subjectId: null,
        title: '',
        items: [],
        analyze: '',
        correct: '',
        score: '',
        difficult: 0
      }
      this.form.id = lastId
    },
    levelChange() {
      this.form.subjectId = null
      this.subjectFilter = this.subjects.filter(data => data.level === this.form.gradeLevel)
    },
    showQuestion() {
      this.questionShow.dialog = true
      this.questionShow.qType = this.form.questionType
      this.questionShow.question = this.form
    },
    ...mapActions('exam', { initSubject: 'initSubject' }),
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      questionTypeEnum: state => state.exam.question.typeEnum,
      levelEnum: state => state.user.levelEnum
    }),
    ...mapState('exam', { subjects: state => state.subjects })
  }
}

</script>
