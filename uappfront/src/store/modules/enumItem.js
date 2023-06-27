// initial state
const state = {
  user: {
    sexEnum: [{ key: 1, value: '男' }, { key: 2, value: '女' }],
    statusEnum: [{ key: 1, value: '启用' }, { key: 2, value: '禁用' }],
    levelEnum: [{ key: 1, value: 'A1' }, { key: 2, value: 'A2' }, { key: 3, value: 'A3' }, { key: 4, value: 'A4' }, { key: 5, value: 'A5' }, { key: 6, value: 'A6' },
      { key: 7, value: 'C1' }, { key: 8, value: 'C2' }, { key: 9, value: 'C3' },
      { key: 10, value: 'G1' }, { key: 11, value: 'G2' }, { key: 12, value: 'G3' }],
    roleEnum: [{ key: 1, value: 'User' }, { key: 2, value: 'Tok' }, { key: 3, value: '管理员' }],
    statusTag: [{ key: 1, value: 'success' }, { key: 2, value: 'danger' }],
    statusBtn: [{ key: 1, value: '禁用' }, { key: 2, value: '启用' }]
  },
  exam: {
    examPaper: {
      paperTypeEnum: [{ key: 1, value: '固定方案' }, { key: 4, value: '定时方案' }, { key: 6, value: '任务方案' }]
    },
    question: {
      typeEnum: [{ key: 1, value: 'Radio' }, { key: 2, value: '多选' }, { key: 3, value: '判断' }, { key: 4, value: '填充文本' }, { key: 5, value: '富文本' }, { key: 6, value: 'Follow' }],
      editUrlEnum: [{ key: 1, value: '/exam/question/edit/singleChoice', name: 'Radio' },
        { key: 2, value: '/exam/question/edit/multipleChoice', name: '多选' },
        { key: 3, value: '/exam/question/edit/trueFalse', name: '判断' },
        { key: 4, value: '/exam/question/edit/gapFilling', name: '填充文本' },
        { key: 5, value: '/exam/question/edit/shortAnswer', name: '富文本' },
        { key: 6, value: '/exam/question/edit/follow', name: 'Follow' }]
    }
  }
}

// getters
const getters = {
  enumFormat: (state) => (arrary, key) => {
    return format(arrary, key)
  }
}

// actions
const actions = {}

// mutations
const mutations = {}

const format = function (array, key) {
  for (let item of array) {
    if (item.key === key) {
      return item.value
    }
  }
  return null
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
