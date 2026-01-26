package org.zkoss.zkspringboot.demo.car;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.springframework.data.domain.Page;
import org.zkoss.zk.ui.Executions;

import java.util.List;


@VariableResolver(DelegatingVariableResolver.class)
public class CarListVM {

    @WireVariable
    private CarService carService;

    private List<Car> cars;
    private String query = "";
    private int page = 0;
    private int size = 10;
    private long total = 0;

    @Init
    @NotifyChange({"cars", "total"})
    public void init() {
        refresh();
    }

    @Command
    @NotifyChange({"cars", "total"})
    public void search() {
        page = 0;
        refresh();
    }

    @Command
    @NotifyChange({"cars", "total"})
    public void nextPage() {
        page++;
        refresh();
    }

    @Command
    @NotifyChange({"cars", "total"})
    public void prevPage() {
        if (page > 0) {
            page--;
            refresh();
        }
    }

    @Command
    @NotifyChange({"cars", "total"})
    public void delete(@BindingParam("id") Long id) {
        carService.delete(id);
        refresh();
    }

    @Command
    public void add() {
        Executions.sendRedirect("/zkau/web/zul/car/detail.zul");
    }

    @Command
    public void edit(@BindingParam("id") Long id) {
        Executions.sendRedirect("/zkau/web/zul/car/detail.zul?id=" + id);
    }

    private void refresh() {
        Page<Car> paged = carService.list(query, page, size);
        cars = paged.getContent();
        total = paged.getTotalElements();
        if (page >= paged.getTotalPages() && page > 0) {
            page = Math.max(0, paged.getTotalPages() - 1);
            paged = carService.list(query, page, size);
            cars = paged.getContent();
            total = paged.getTotalElements();
        }
    }

    // getters and setters
    public List<Car> getCars() {
        return cars;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }
}
