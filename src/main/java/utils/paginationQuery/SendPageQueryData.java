package utils.paginationQuery;

import lombok.Data;

import java.util.List;


/**
 * 分页查询数据发送类——后端返回数据查询结果
 *
 * @author SeptMeteor
 * @date 2023-02-06 06:16:29
 */
@Data
public class SendPageQueryData<T> {

    /**
     * 数据列表：前端当前页面数据列表
     * 使用泛型T：使该类能够发送各种数据给前端
     */
    private List<T> dataList;

    /**
     * 数据量：前端页面数据总量
     */
    private int dataVolume;
}
