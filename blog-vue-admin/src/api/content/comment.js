import request from '@/utils/request'


// 查询评论列表
export function listComment(query) {
    return request({
        url: '/content/comment/list',
        method: 'get',
        params: query
    })
}


// 删除评论
export function delComment(id) {
    return request({
        url: '/content/comment/' + id,
        method: 'delete'
    })
}