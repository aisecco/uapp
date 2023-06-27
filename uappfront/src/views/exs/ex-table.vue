<template>
    <div class="app-container">
        <!-- stripe：条纹，斑马线 -->
        <!-- border：边框设置 -->
        <!-- height：固定表头 -->
        <!-- row-class-name：设置表行class -->
        <!-- max-height：指定最大高度 -->
        <el-table :data='tableData1' stripe :border='true' max-height='300px'>
            <el-table-column width="250px" prop='date' label='日期' :formatter='dateFormat' fixed='left'></el-table-column>
            <el-table-column width="250px" prop='name' label='姓名'></el-table-column>
            <el-table-column width="250px" prop='address' label='地址'></el-table-column>
            <el-table-column width="50px" prop='start' label='start'></el-table-column>
            <el-table-column width="50px" prop='end' label='end'></el-table-column>
            <el-table-column width="250px" fixed='right' label='操作'>
                <template slot-scope='scope'>
                    <el-button @click='showName(scope.row)'>按钮</el-button>
                    <el-button @click='removeCol(scope.row, scope.$index)'>移除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- highlight-current-row实现单选 -->
        <el-table :data='tableData2' height='500px' highlight-current-row ref='tableRef2'
            @current-change='handleCurrentChange' @selection-change='handleSelectionChange'
            :default-sort='{ prop: "date", order: "asccending" }'>
            <el-table-column type='selection'></el-table-column>
            <el-table-column type='index' label='序号' width='50px' :index='indexMethod'></el-table-column>
            <el-table-column prop='date' label='日期' :formatter='dateFormat' sortable column-key='date'
                :filters="[{ text: '1999-5-29', value: '1999-5-29' }, { text: '1998-5-29', value: '1998-5-29' }]"
                :filter-method="filterDate"></el-table-column>
            <el-table-column label='配送信息'>
                <el-table-column prop='name' label='姓名' sortable
                    :filters='[{ text: "唐三", value: "唐三" }, { text: "唐舞麟", value: "唐舞麟" }, { text: "王小虎", value: "王小虎" }]'
                    :filter-method='filterHandler'></el-table-column>
                <el-table-column label='地址'>
                    <el-table-column prop='province' label='省份'></el-table-column>
                    <el-table-column prop='city' label='市区'></el-table-column>
                    <el-table-column prop='address' label='地址'></el-table-column>
                    <el-table-column prop='email' label='邮编'></el-table-column>
                </el-table-column>
            </el-table-column>
        </el-table>

        <div>
            <el-button @click='setCurrent(tableData2[2])'>选择第三行</el-button>
            <el-button @click='setCurrent'>取消选择</el-button>
            <el-button @click='toggleSelection([tableData2[1], tableData2[2]])'>切换二三两行的选中状态</el-button>
            <el-button @click='toggleSelection'>取消选中</el-button>
            <el-button @click='clearFilterDate'>清除日期过滤器</el-button>
            <el-button @click='clearFilter'>清楚所有过滤器</el-button>
        </div>

        <el-table :data='tableData2'>
            <el-table-column label='日期' prop='date' :formatter="dateFormat"></el-table-column>
            <el-table-column label='姓名'>
                <template slot-scope='scope'>
                    <el-popover trigger='hover' placement='top'>
                        <p>姓名：{{ scope.row.name }}</p>
                        <p>住址：{{ scope.row.address }}</p>
                        <div slot='reference' class='name-wrapper'>
                            <el-tag size='medium'>{{ scope.row.name }}</el-tag>
                        </div>
                    </el-popover>
                </template>
            </el-table-column>
            <el-table-column>
                <template slot-scope='scope'>
                    <el-button size='mini'>编辑</el-button>
                    <el-button size='mini' type='danger'>删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-table :data='tableData2'>
            <el-table-column type='expand'>
                <template slot-scope='{row}'>
                    <el-form label-position='left' class='demo-table-expand'>
                        <el-form-item label='姓名'>
                            <span>{{ row.name }}</span>
                        </el-form-item>
                        <el-form-item label='省份'>
                            <span>{{ row.province }}</span>
                        </el-form-item>
                        <el-form-item label='城市'>
                            <span>{{ row.city }}</span>
                        </el-form-item>
                        <el-form-item label='地址'>
                            <span>{{ row.address }}</span>
                        </el-form-item>
                        <el-form-item label='日期'>
                            <span>{{ dateFormat(row) }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column prop='name' label="姓名"></el-table-column>
        </el-table>
        <el-table :data='tableData3' :border='true' row-key='id' default-expand-all
            :tree-props='{ children: "children", hasChildren: "hasChildren" }'>
            <el-table-column prop='name' label='姓名'></el-table-column>
        </el-table>
        <el-table :data='tableData1.filter(data => !search || data.name.includes(search))' show-summary
            :summary-method='getSummaries' :span-method='arraySpanMethod'>
            <el-table-column prop='date' label='日期' :formatter='dateFormat'></el-table-column>
            <el-table-column prop='name' label='姓名'></el-table-column>
            <el-table-column prop='address' label='地址'></el-table-column>
            <el-table-column prop='money' label='金额'></el-table-column>
            <el-table-column>
                <template slot='header' slot-scope="scope">
                    <el-input v-model='search' size='mini' placeholder='请输入关键字搜索'></el-input>
                </template>
                <template slot-scope='scope'>
                    <el-button size='mini'>修改</el-button>
                    <el-button size='mini' type='danger'>删除</el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>
  
<script>
export default {
    name: "uploadFile",
    props: {
        uploadParams: Object,
        paramsData: Object
    },
    data() {
        return {
            tableData1: [
                { date: new Date(), name: '唐三', address: '斗罗大陆', money: 1000, start: 1.12, end: 2.34 },
                { date: new Date(), name: '荣念冰', address: '魔法帝国', money: 10000, start: 2.12, end: 3.34 },
                { date: new Date(), name: '唐舞麟', address: '神界', money: 1000000, start: 3.12, end: 5.34 },
                { date: new Date(), name: '柔骨兔', address: '斗罗大陆', money: 1000000, start: 5.62, end: 7.34 }
            ],
            tableData2: [
                { date: new Date("1999-05-29"), name: '唐三', province: '安徽', city: '合肥', address: "安徽省合肥市肥西县北村花园1501弄", email: '235000' },
                { date: new Date("1998-05-29"), name: '荣念冰', province: '安徽', city: '合肥', address: "安徽省合肥市肥西县北村花园1501弄", email: '235000' },
                { date: new Date("1997-05-29"), name: '唐舞麟', province: '安徽', city: '合肥', address: "安徽省合肥市肥西县北村花园1501弄", email: '235000' },
                { date: new Date("1995-05-29"), name: '柔骨兔', province: '安徽', city: '合肥', address: "安徽省合肥市肥西县北村花园1501弄", email: '235000' },
                { date: new Date("1996-05-29"), name: '王小虎', province: '安徽', city: '合肥', address: "安徽省合肥市肥西县北村花园1501弄", email: '235000' },
                { date: new Date(), name: '王小虎', province: '安徽', city: '合肥', address: "安徽省合肥市肥西县北村花园1501弄", email: '235000' },
                { date: new Date(), name: '王小虎', province: '安徽', city: '合肥', address: "安徽省合肥市肥西县北村花园1501弄", email: '235000' },
                { date: new Date(), name: '王小虎', province: '安徽', city: '合肥', address: "安徽省合肥市肥西县北村花园1501弄", email: '235000' },
                { date: new Date(), name: '王小虎', province: '安徽', city: '合肥', address: "安徽省合肥市肥西县北村花园1501弄", email: '235000' },
            ],

            tableData3: [
                { id: 1, name: '爷爷', children: [{ id: 12, name: '爸爸', children: [{ id: 123, name: '儿子' }] }] }
            ],
            currentRow: null,
            mutipleSelection: [],
            search: '',
        }
    },
    created() { },
    methods: {
        indexMethod(index) {
            return index * 2;
        },
        arraySpanMethod({ row, column, rowIndex, columnIndex }) {
            if (rowIndex % 2 === 0) {
                if (columnIndex === 0) {
                    return [1, 2]
                } else if (columnIndex === 1) {
                    return [0, 0]
                }
            }
            if (columnIndex === 2) {
                if (rowIndex % 2 === 0) {
                    return [2, 1]
                } else if (rowIndex % 2 === 1) {
                    return [0, 0]
                }
            }
        },
        getSummaries(param) {
            let { columns, data } = param
            let sums = []
            columns.forEach((column, index) => {
                if (index === 0) {
                    sums[index] = '总价'
                    return
                }
                let values = data.map(item => Number(item[column.property]))
                if (!values.every(value => isNaN(value))) {
                    sums[index] = values.reduce((prev, curr) => {
                        let value = Number(curr)
                        if (!isNaN(value)) {
                            return prev + curr
                        } else {
                            return prev
                        }
                    }, 0)
                    sums[index] += ' 元'
                } else {
                    sums[index] = 'N/A'
                }
            })
            return sums
        },
        //日期格式化钩子
        dateFormat(row) {
            let date = row.date
            return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`
        },
        //按钮钩子
        showName(row) {
            console.log(row.name)
        },
        //移除按钮钩子
        removeCol(row, index) {
            this.tableData1.splice(index, 1)
        },
        //设置高亮钩子
        setCurrent(row) {
            this.$refs['tableRef2'].setCurrentRow(row)
        },
        //高亮改变钩子
        handleCurrentChange(currentRow, oldCurrentRow) {
            this.currentRow = currentRow
        },
        //高亮
        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs['tableRef2'].toggleRowSelection(row)
                });
            } else {
                this.$refs['tableRef2'].clearSelection();
            }
        },
        //选中改变钩子
        handleSelectionChange(rows) {
            this.mutipleSelection = rows;
        },
        //姓名筛选钩子
        filterHandler(value, row, column) {
            let prop = column['property'];
            return row[prop] === value;
        },
        //日期筛选钩子
        filterDate(value, row, column) {
            let prop = column['property'];
            return `${row[prop].getFullYear()}-${row[prop].getMonth() + 1}-${row[prop].getDate()}` == value
        },
        clearFilterDate() {
            this.$refs['tableRef2'].clearFilter(`date`)
        },
        clearFilter() {
            this.$refs['tableRef2'].clearFilter();
        }
    }
}
</script>