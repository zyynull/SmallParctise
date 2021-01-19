package demo.demo.infra.utils.common;


import java.util.List;

/**
 * @Author: Mo Zhipeng
 * @Description:
 * @Date: 2019/8/8 23:03
 * @Modified: Last modified by
 */
public class Pagination {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    // 当前页
    private int page = DEFAULT_PAGE;
    // 页大小
    private int pageSize = DEFAULT_PAGE_SIZE;
    // 总页数
    private int pages;
    // 总条数
    private int total;
    // 前一页
    private int prevPage;
    // 下一页
    private int nextPage;

    // 数据
    private List data;

    /**
     * 更改 pagination 中的数据
     *
     * @param data 新数据
     * @return this
     */
    public Pagination data(List data) {
        this.data = data;
        return this;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {

        return total;
    }

    public void setTotal(int total) {


        //总页数
        pages = total / pageSize;

        //当前页的数量
        int size = total % pageSize;
        if (page < pages) {
            if (page > 1) {
                prevPage = page - 1;
            }
            nextPage = page + 1;
        } else {
            prevPage = page - 1;
            pageSize = size;
        }

        this.total = total;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }


}
