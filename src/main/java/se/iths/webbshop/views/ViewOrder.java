package se.iths.webbshop.views;

import se.iths.webbshop.entities.Order;

import java.util.List;
import java.util.stream.Collectors;

public class ViewOrder {
    public int id;
    public Integer user;
    public String status;
    public List<ViewLine> lines;

    public ViewOrder() {
    }

    public ViewOrder(Order order) {
        this.id = order.getId();
        this.user = order.getUser().getId();
        this.status = order.getStatus();
        this.lines = order.getLines().stream().map(ViewLine::new).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ViewLine> getLines() {
        return lines;
    }

    public void setLines(List<ViewLine> lines) {
        this.lines = lines;
    }
}
