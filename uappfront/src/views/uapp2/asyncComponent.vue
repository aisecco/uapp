<script>
import { defineAsyncComponent } from 'vue'

export default {
    components: {
        Part: defineAsyncComponent(() => import('./part1.vue')
        )
    }
}

// const AsyncComp = defineAsyncComponent(() => {
//     return new Promise((resolve, reject) => {
//         // ...从服务器获取组件
//         import('./part2.vue')
//         resolve(/* 获取到的组件 */)
//     })
// })

// const AsyncComp = defineAsyncComponent({
//   // 加载函数
//   loader: () => import('./part2.vue'),

//   // 加载异步组件时使用的组件
//   loadingComponent: LoadingComponent,
//   // 展示加载组件前的延迟时间，默认为 200ms
//   delay: 200,

//   // 加载失败后展示的组件
//   errorComponent: ErrorComponent,
//   // 如果提供了一个 timeout 时间限制，并超时了
//   // 也会显示这里配置的报错组件，默认值是：Infinity
//   timeout: 3000
// })


</script>

<template>
    <div>
        异步加载的Part1：
        <div>
            <Part />
        </div>

    </div>
</template>
