<template>
    <div class="ucom2">
        <h1>Hello! {{ user_name }}</h1>
    </div>
</template>
  
<script>
export default {
    name: 'ucom2',
    data() {
        return {
            user_name: "ucom2-1 name"
        }
    }
}
</script>
  
  <!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.ucom2 {
    color: skyblue;
}
</style>
  