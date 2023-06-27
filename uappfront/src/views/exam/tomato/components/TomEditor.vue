<template>
    <div class="app-container">
        <audio :src="tomato.src" controls ref="audio" @play='audioPlaying' style='width: 100%; display: none'></audio>
        <el-form label-width="100px">
            <el-form-item>
                <el-button type="primary" @click="tomatoRestart">|-</el-button>
                <el-button type="primary" @click="tomatoAllstop">Allstop</el-button>
                <el-button type="primary" @click="tomatoPause">暂停</el-button>
                <el-button type="primary" @click="tomatoResume">继续</el-button>
            </el-form-item>
            <el-form-item>
                <el-input-number v-model="now" :precision="2" :step="0.1" :max="100"></el-input-number>
                {{ currentChunk }}
                <el-button type="primary" @click="chunkCreate">Append</el-button>
                <el-button type="primary" @click="chunkPrev">Prev</el-button>
                <el-button type="primary" @click="tryPlay">Try Play</el-button>
                <el-button type="primary" @click="chunkNext">Next</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="setPoint">One stop</el-button>

            </el-form-item>
        </el-form>
        <el-table :data="tomato.chunks">
            <el-table-column label="text" align="center">
                <template slot-scope="scope">
                    <el-input v-model="scope.row.text" />
                    <el-input v-model="scope.row.textcn" />
                </template>
            </el-table-column>
            <el-table-column width="150px" label="Start/End" align="center">
                <template slot-scope="scope">
                    <el-input-number size="small" controls-position="right" v-model="scope.row.start" :precision="2"
                        :step="0.1" :max="100"></el-input-number>
                    <el-input-number size="small" controls-position="right" v-model="scope.row.end" :precision="2"
                        :step="0.1" :max="100"></el-input-number>
                </template>
            </el-table-column>
            <el-table-column width="150px" align="center" fixed='right' label='操作'>
                <template slot-scope='scope'>
                    <el-button type="text" @click="removeChunk(scope.$index)">remove</el-button>
                    <el-button type="text" @click="tryPlayChunk(scope.$index)">{{ (scope.row.ui == null || typeof
                        scope.row.ui == 'undefined') ? 'test' :
                        scope.row.ui
                    }}</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    data() {
        return {
            allstop: true,
            trymode: false,
            timerPeriod: 100,
            currentChunk: 0,
            now: 0,
            tomato: {},
            msg: 'Hello world!',
        }
    },
    created() {
        console.log('Editor created!')
    },
    methods: {
        say(msg) {
            console.log('say:', msg)
        },
        audioPlaying() {
            // console.log('on audioPlayed')

            if (this.allstop) {
                console.log('allstop was set')
                return
            }

            // if (this.playing )
            {
                setTimeout(() => {
                    this.audioPlaying()
                }, this.timerPeriod)
            }

            // console.log('this...', this)

            let audio = this.$refs.audio
            if (audio == null || typeof (audio) == 'undefined') {
                // console.log('audio error!', audio)
                return
            }

            if (audio == null || typeof (audio.readyState) == 'undefined' || audio.readyState < 4) {
                console.log('readyState error! continue')
                // return
            }

            // this.form.now1 = audio.currentTime
            if (audio == null || typeof (audio.currentTime) == 'undefined') {
                console.log(`currentTime error!`)
            } else {
                // this.form.current = audio.currentTime
                // console.log(`currentTime: `, audio.currentTime, `duration: `, audio.duration)
                console.log(`this.currentChunk: `, this.currentChunk)
                this.now = audio.currentTime

                if (this.tomato != null && this.tomato.chunks != null) {
                    let chunk = this.tomato.chunks[this.currentChunk]
                    console.log(`start: `, chunk.start, `end: `, chunk.end)
                    if (this.trymode && chunk.end > 0 && audio.currentTime > chunk.end) {
                        console.log('end to pause')
                        audio.pause()
                    }
                } else {
                    console.log('tomato.chunks:', this.tomato.chunks)
                }
            }
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
        chunkInit() {
            console.log('chunkInit() tomato div init: ', this.tomato, this.tomato.chunks)

            let oid = 1;
            for (let c of this.tomato.chunks) {
                c.oid = oid++
                c.ui = "test_" + c.oid
            }
        },
        chunkCreate() {
            console.log('chunkCreate(), this.tomato=', this.tomato)
            let t = this.tomato
            if (t.chunks == null || typeof (t.chunks) === 'undefined') { t.chunks = [] }
            let newid = t.chunks.length + 1
            let c = {
                "oid": newid,
                "text": "",
                "textcn": "",
                "start": 0,
                "end": 0,
            }
            t.chunks.push(c)
            return c
        },
        chunkHead() {
            this.currentChunk = 0
        },
        chunkPrev() {
            if (this.currentChunk > 0) {
                this.currentChunk--
            }
        },
        chunkNext() {
            if (this.currentChunk < this.tomato.chunks.length) {
                this.currentChunk++
            }
        },
        chunkLast() {
            this.currentChunk = this.tomato.chunks.length - 1
        },
        tryPlayChunk(index) {
            console.log('tryPlayChunk ', index, this.currentChunk)
            this.trymode = true
            this.allstop = false
            this.currentChunk = index
            console.log('tryPlayChunk ', index, this.currentChunk)
            let chunk = this.tomato.chunks[index]

            if (chunk.status == null || typeof chunk.status === 'undefined' || chunk.status == 0) {
                chunk.status = 1
                chunk.ui = 'testing...'
            }
            else {
                chunk.status = 0
                chunk.ui = 'test'
            }

            console.log('chunk.status: ', chunk.status, `ui: `, chunk.ui, `start: `, chunk.start, `end: `, chunk.end)

            // let audio = this.$refs.audio
            // console.log(`currentTime: `, audio.currentTime, `duration: `, audio.duration)
            this.$refs.audio.currentTime = chunk.start
            this.$refs.audio.play()
        },
        tryPlay() {
            console.log('tryPlay, currentChunk: ', this.currentChunk)
            console.log('tomato.chunks: ', this.tomato.chunks)

            if (this.tryplayStopped) {
                this.currentChunk++;
                console.log('tryPlay, currentChunk: ', this.currentChunk)
            }
            this.trymode = true
            this.allstop = false

            let audio = this.$refs.audio

            console.log(`currentTime: `, audio.currentTime, `duration: `, audio.duration)

            let chunk = this.tomato.chunks[this.currentChunk]
            // console.log(`start: `, chunk.start, `end: `, chunk.end)
            this.$refs.audio.currentTime = chunk.start
            this.$refs.audio.play()

            // const length = audio.played.length
            // console.log(`length: ${length}`)

            // for (var i = 0; i < length; i++) {
            //     var start = audio.played.start(i)
            //     var end = audio.played.end(i)

            //     console.log(`index: ${i}, start: ${start}, end: ${end}, durations: ${end - start}s`)
            // }
        },
        setPoint() {
            // all step from 0 chunk to all chunks
            let audio = this.$refs.audio
            console.log(`currentTime: `, audio.currentTime, `duration: `, audio.duration)
            if (this.tomato.chunks == null || typeof this.tomato.chunks === 'undefined') {
                this.tomato.chunks = []
            }

            let chunk = null
            if (this.tomato.chunks.length == 0) {
                chunk = this.chunkCreate()
            }

            // if ( this.tomato.chunks.length < this.currentChunk + 1) {
            //     chunk = this.chunkCreate()
            // }
            this.chunkLast();
            chunk = this.tomato.chunks[this.currentChunk]
            console.log('current chunk:', chunk)

            chunk.end = audio.currentTime
            console.log('set the end point in current chunk:', chunk.end)

            // // apend next chunk
            this.currentChunk++
            if (this.tomato.chunks.length < this.currentChunk + 1) {
                chunk = this.chunkCreate()
            }
            else {
                chunk = this.tomato.chunks[this.currentChunk]
                console.log('default, set to next ', chunk.oid)
            }
            chunk.start = audio.currentTime
            chunk.end = audio.duration
        },

        removeChunk(i) {
            this.$confirm('确认删除？', '提示框').then(() => {
                // console.log('确认')
                this.tomato.chunks.splice(i, 1)
                this.currentChunk = this.tomato.chunks.length
            }).catch(() => {
                // console.log('取消')
            })
        },
        removeAllChunks() {
            this.tomato.chunks = []
            this.currentChunk = this.tomato.chunks.length
        },
    }
}
</script>

<style>
.example {
    color: green;
}
</style>

<custom1>
  This could be e.g. documentation for the component.
</custom1>