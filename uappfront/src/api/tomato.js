import { post } from '@/utils/request'

export default {
  pageList: query => post('/api/admin/exam/tomato/page', query),
  edit: query => post('/api/admin/exam/tomato/edit', query),
  select: id => post('/api/admin/exam/tomato/select/' + id),
  delete: id => post('/api/admin/exam/tomato/delete/' + id),
  duplicate: id => post('/api/admin/exam/tomato/duplicate/' + id),
}
