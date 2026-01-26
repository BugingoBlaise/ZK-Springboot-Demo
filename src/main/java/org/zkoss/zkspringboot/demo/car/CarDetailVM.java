package org.zkoss.zkspringboot.demo.car;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

@VariableResolver(DelegatingVariableResolver.class)
public class CarDetailVM {

    @WireVariable
    private CarService carService;

    private Car car;

    @Init
    @NotifyChange("car")
    public void init(@QueryParam("id") Long id) {
        if (id != null) {
            car = carService.findById(id).orElse(new Car());
        } else {
            car = new Car();
        }
    }

    @Command
    public void cancel() {
        Executions.sendRedirect("/zkau/web/zul/car/list.zul");
    }

    @Command
    public void save() {
        carService.save(car);
        Executions.sendRedirect("/zkau/web/zul/car/list.zul");
    }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
}
