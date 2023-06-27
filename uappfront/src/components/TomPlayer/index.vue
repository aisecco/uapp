<template>
    <div class="example" @click="aaaaaa">{{ msg }}</div>
</template>

<script>
export default {
    data() {
        return {
            msg: 'Hello world!'
        }
    },
    created() {
        console.log('created!')
        // let id = this.$route.query.id

        // let _this = this
        // this.initSubject(function () {
        //     _this.subjectFilter = _this.subjects
        // })
        // if (id && parseInt(id) !== 0) {
        //     _this.formLoading = true
        //     questionApi.select(id).then(re => {
        //         _this.form = re.response
        //         _this.formLoading = false
        //     })
        // }
    },
    methods: {
        aaaaaa() {

            console.log('aaa...')
        },
        say(msg) {
            console.log('say:', msg)
        },
        audioPlaying() {
            console.log('audioPlaying...')
        }

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