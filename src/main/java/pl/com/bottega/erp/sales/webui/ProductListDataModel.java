package pl.com.bottega.erp.sales.webui;


import javax.faces.model.DataModel;
import java.util.List;

public class ProductListDataModel<T> extends DataModel<T>
{
    private Integer totalRowsCount;
    private Integer pageSize;
    private Integer currentPage;
    private List<T> data;
    private Integer rowIndex;
    private Integer numberOfPages;
    private String sortField;
    private Boolean sortOrder;

    public ProductListDataModel() {
        this.currentPage = 1;
        this.pageSize = 10;
        sortOrder = true;
    }

    @Override
    public boolean isRowAvailable() {
        if(this.data == null) {
            return false;
        }
        return this.rowIndex >= 0 && this.rowIndex < this.data.size();
    }

    @Override
    public int getRowCount() {
        if(this.data == null) {
            return -1;
        }
        return this.data.size();
    }

    @Override
    public T getRowData() {
        return this.data.get(getRowCount());
    }

    @Override
    public int getRowIndex() {
        return this.rowIndex;
    }

    @Override
    public void setRowIndex(int i) {
        this.rowIndex = rowIndex;
    }

    @Override
    public Object getWrappedData() {
       return data;
    }

    @Override
    public void setWrappedData(Object o) {
        this.data = (List<T>) data;
    }
}
