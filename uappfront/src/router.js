import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'
import sfc2 from '@/views/uapp2/sfc2'

Vue.use(Router)

let part2url = '@/views/uapp2/part2'

const constantRoutes = [{
  path: '/redirect',
  component: Layout,
  hidden: true,
  children: [{
    path: '/redirect/:path*',
    component: () => import('@/views/redirect/index')
  }]
},
{
  path: '/login',
  name: 'Login',
  hidden: true,
  component: () => import('@/views/login/index'),
  meta: { title: '登录' }
},
{
  path: '/',
  component: Layout,
  redirect: '/dashboard',
  children: [{
    path: 'dashboard',
    component: () => import('@/views/dashboard/index'),
    name: 'Dashboard',
    meta: { title: '主页', icon: 'home', affix: true }
  }]
},
{
  path: '/user',
  component: Layout,
  name: 'UserPage',
  meta: {
    title: '用户管理',
    icon: 'users'
  },
  children: [{
    path: 'student/list',
    component: () => import('@/views/user/student/list'),
    name: 'UserStudentPageList',
    meta: { title: 'User列表', noCache: true }
  },
  {
    path: 'student/edit',
    component: () => import('@/views/user/student/edit'),
    name: 'UserStudentEdit',
    meta: { title: 'User编辑', noCache: true, activeMenu: '/user/student/list' },
    hidden: true
  },
  {
    path: 'admin/list',
    component: () => import('@/views/user/admin/list'),
    name: 'UserAdminPageList',
    meta: { title: '管理员列表', noCache: true }
  },
  {
    path: 'admin/edit',
    component: () => import('@/views/user/admin/edit'),
    name: 'UserAdminEdit',
    meta: { title: '管理员编辑', noCache: true, activeMenu: '/user/admin/list' },
    hidden: true
  }
  ]
},
{
  path: '/tomato',
  component: Layout,
  name: 'LibPage',
  meta: {
    title: '库管理',
    icon: 'exam'
  },
  children: [{
    path: 'tomatolist',
    component: () => import('@/views/exam/tomato/tomatolist'),
    name: 'LibTomatoPageList',
    meta: { title: '静态库表', noCache: true }
  },
  {
    path: 'tomatoedit',
    component: () => import('@/views/exam/tomato/tomatoedit'),
    name: 'LibTomatoEdit',
    meta: { title: '静态库表编辑', noCache: true },
    hidden: true
  },
  {
    path: 'chunklist',
    component: () => import('@/views/exam/tomato/chunklist'),
    name: 'LibChunkPageList',
    meta: { title: '静态库元', noCache: true }

  }
    // ,
    // {
    //   path: 'tomatoupload',
    //   component: () => import('@/views/exam/tomato/tomatoupload'),
    //   name: 'LibTomatoupload',
    //   meta: { title: '文件上传', noCache: true },
    //   hidden: true
    // }
  ]
},
{
  path: '/exam',
  component: Layout,
  name: 'ExamPage',
  meta: {
    title: '方案管理',
    icon: 'exam'
  },
  children: [{
    path: 'paper/list',
    component: () => import('@/views/exam/paper/list'),
    name: 'ExamPaperPageList',
    meta: { title: '方案列表', noCache: true }
  },
  {
    path: 'paper/edit',
    component: () => import('@/views/exam/paper/edit'),
    name: 'ExamPaperEdit',
    meta: { title: '预案编排', noCache: true, activeMenu: '/exam/paper/list' },
    hidden: true
  },
  {
    path: 'question/tomatolist',
    component: () => import('@/views/exam/question/tomatolist'),
    name: 'TomatoPageList',
    meta: { title: '静态库question', noCache: true }
  },
  {
    path: 'question/list',
    component: () => import('@/views/exam/question/list'),
    name: 'ExamQuestionPageList',
    meta: { title: '传统库元', noCache: true }
  },
  {
    path: 'question/edit/singleChoice',
    component: () => import('@/views/exam/question/edit/single-choice'),
    name: 'singleChoicePage',
    meta: { title: 'Radio编辑', noCache: true, activeMenu: '/exam/question/list' },
    hidden: true
  },
  {
    path: 'question/edit/multipleChoice',
    component: () => import('@/views/exam/question/edit/multiple-choice'),
    name: 'multipleChoicePage',
    meta: { title: '多选编辑', noCache: true, activeMenu: '/exam/question/list' },
    hidden: true
  },
  {
    path: 'question/edit/trueFalse',
    component: () => import('@/views/exam/question/edit/true-false'),
    name: 'trueFalsePage',
    meta: { title: '判断编辑', noCache: true, activeMenu: '/exam/question/list' },
    hidden: true
  },
  {
    path: 'question/edit/gapFilling',
    component: () => import('@/views/exam/question/edit/gap-filling'),
    name: 'gapFillingPage',
    meta: { title: '填充文本编辑', noCache: true, activeMenu: '/exam/question/list' },
    hidden: true
  },
  {
    path: 'question/edit/shortAnswer',
    component: () => import('@/views/exam/question/edit/short-answer'),
    name: 'shortAnswerPage',
    meta: { title: '富文本编辑', noCache: true, activeMenu: '/exam/question/list' },
    hidden: true
  },
  {
    path: 'question/edit/follow',
    component: () => import('@/views/exam/question/edit/follow'),
    name: 'follow',
    meta: { title: 'Follow', noCache: true, activeMenu: '/exam/question/list' },
    hidden: true
  }
  ]
},
{
  path: '/task',
  component: Layout,
  name: 'TaskPage',
  meta: {
    title: '任务管理',
    icon: 'task'
  },
  alwaysShow: true,
  children: [{
    path: 'list',
    component: () => import('@/views/task/list'),
    name: 'TaskListPage',
    meta: { title: '任务列表', noCache: true }
  },
  {
    path: 'edit',
    component: () => import('@/views/task/edit'),
    name: 'TaskEditPage',
    meta: { title: '任务创建', noCache: true }
  }
  ]
},
{
  path: '/education',
  component: Layout,
  name: 'EducationPage',
  meta: {
    title: '系统管理',
    icon: 'education'
  },
  alwaysShow: true,
  children: [{
    path: 'subject/list',
    component: () => import('@/views/education/subject/list'),
    name: 'EducationSubjectPage',
    meta: { title: 'Subject列表', noCache: true }
  },
  {
    path: 'subject/edit',
    component: () => import('@/views/education/subject/edit'),
    name: 'EducationSubjectEditPage',
    meta: { title: 'Subject编辑', noCache: true, activeMenu: '/education/subject/list' },
    hidden: true
  }
  ]
},
{
  path: '/answer',
  component: Layout,
  name: 'AnswerPage',
  meta: {
    title: 'Done',
    icon: 'answer'
  },
  alwaysShow: true,
  children: [{
    path: 'list',
    component: () => import('@/views/answer/list'),
    name: 'AnswerPageList',
    meta: { title: 'Done列表', noCache: true }
  }]
},
{
  path: '/message',
  component: Layout,
  name: 'MessagePage',
  meta: {
    title: '消息中心',
    icon: 'message'
  },
  alwaysShow: true,
  children: [{
    path: 'list',
    component: () => import('@/views/message/list'),
    name: 'MessageListPage',
    meta: { title: '消息列表', noCache: true }
  },
  {
    path: 'send',
    component: () => import('@/views/message/send'),
    name: 'MessageSendPage',
    meta: { title: '消息发送', noCache: true }
  }
  ]
},
{
  path: '/uapp1',
  component: Layout,
  name: 'Uapp1Page',
  meta: {
    title: 'uapp1 name',
    icon: 'message'
  },
  alwaysShow: true,
  children: [{
    path: 'list',
    component: () => import('@/views/uapp1/list'),
    name: 'Uapp1ListPage',
    meta: { title: 'uapp1列表', noCache: true }
  },
  {
    path: 'send',
    component: () => import('@/views/uapp1/send'),
    name: 'Uapp1SendPage',
    meta: { title: 'uapp1表单', noCache: true }
  }
  ]
},
{
  path: '/uapp2',
  component: Layout,
  name: 'Uapp2Page',
  meta: {
    title: 'uapp2 name',
    icon: 'message'
  },
  alwaysShow: true,
  children: [{
    path: 'list',
    component: () => import('@/views/uapp2/list'),
    name: 'Uapp2ListPage',
    meta: { title: 'uapp2列表', noCache: true }
  },
  {
    path: 'send',
    component: () => import('@/views/uapp2/send'),
    name: 'Uapp2SendPage',
    meta: { title: 'uapp2表单', noCache: true }
  },
  {
    path: 'container',
    component: () => import('@/views/uapp2/ucontainer2'),
    name: 'Ucontainer2',
    meta: { title: 'Ucontainer2', noCache: true }
  },
  {
    path: 'part',
    component: () => import('@/views/uapp2/part2'),
    name: 'Uapp2Part',
    meta: { title: 'uapp2Part', noCache: true }
  },
  {
    path: 'sfc',
    component: () => import('@/views/uapp2/sfc'),
    name: 'sfc',
    meta: { title: 'sfc', noCache: true }
  },
  {
    path: 'optionsAPI',
    component: () => import('@/views/uapp2/optionsAPI'),
    name: 'optionsAPI',
    meta: { title: 'optionsAPI', noCache: true }
  },
  {
    path: 'CompositionAPI',
    component: () => import('@/views/uapp2/compositionAPI'),
    name: 'compositionAPI',
    meta: { title: 'compositionAPI', noCache: true }
  },
  {
    path: 'asyncComponent',
    component: () => import('@/views/uapp2/asyncComponent'),
    name: 'asyncComponent',
    meta: { title: 'asyncComponent', noCache: true }
  }
  ]
},
{
  path: '/log',
  component: Layout,
  name: 'LogPage',
  meta: {
    title: '日志中心',
    icon: 'log'
  },
  alwaysShow: true,
  children: [{
    path: 'user/list',
    component: () => import('@/views/log/list'),
    name: 'LogUserPage',
    meta: { title: '用户日志', noCache: true }
  }]
},
{
  path: '/exs',
  component: Layout,
  name: 'Exercise',
  meta: {
    title: '组件',
    icon: 'log'
  },
  alwaysShow: true,
  children: [{
    path: 'exs/ex-table',
    component: () => import('@/views/exs/ex-table'),
    name: 'exTable',
    meta: { title: 'table', noCache: true }
  },
  {
    path: 'exs/ex-dialog',
    component: () => import('@/views/exs/ex-dialog'),
    name: 'exDialog',
    meta: { title: '弹出对话框', noCache: true }
  },
  {
    path: 'exs/upload',
    component: () => import('@/views/exs/upload'),
    name: 'upload',
    meta: { title: 'upload', noCache: true }
  }
  ]
},
{
  path: '/profile',
  component: Layout,
  hidden: false,
  children: [{
    path: 'index',
    component: () => import('@/views/profile/index'),
    name: 'Profile',
    meta: { title: '个人简介', icon: 'user', noCache: true }
  }]
},
{
  path: '*',
  hidden: true,
  component: () => import('@/views/error-page/404'),
  meta: { title: '404', noCache: true }
}
]

const router = new Router({
  routes: constantRoutes
})

export {
  constantRoutes,
  router
}
