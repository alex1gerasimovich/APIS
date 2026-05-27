package com.warehouse.command;

import com.warehouse.exception.WarehouseException;

public interface Command {
    void execute() throws WarehouseException;
}
