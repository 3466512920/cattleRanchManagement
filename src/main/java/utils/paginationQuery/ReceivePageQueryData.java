package utils.paginationQuery;

import lombok.Data;

/**
 * 分页查询数据接收类——接收前端页面发送的查询参数
 *
 * @author SeptMeteor
 * @date 2023-02-06 06:18:30
 */
@Data
public class ReceivePageQueryData {

    /**
     * 查询名称：模糊查询名
     */
    private String queryName;

    /**
     * 当前页面：当前为第几页
     */
    private int currentPage;

    /**
     * 页面尺寸：最大数据量
     */
    private int pageSize;
}
