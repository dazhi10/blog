import request from '@/utils/request'


// 查询评论列表
export function listLog(query) {
    return request({
        url: '/content/log/list',
        method: 'get',
        params: query
    })
}


// 删除评论
export function delLog(id) {
    return request({
        url: '/content/log/' + id,
        method: 'delete'
    })
}