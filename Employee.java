package org.darmokhval.tasks6;

import java.math.BigDecimal;

public abstract class Employee {

    private final String lastName;
    private final BigDecimal salary;
    private BigDecimal bonus;

    public String getLastName() {
        return lastName;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public BigDecimal getBonus() {
        return bonus;
    }
    public abstract void setBonus(BigDecimal bonus);
    public BigDecimal toPay() {
        return salary.add(bonus);
    }
    protected void updateBonus(BigDecimal bonus) { //?
        this.bonus = bonus;
    }

    public Employee(String lastName, BigDecimal salary) {
        this.lastName = lastName;
        this.salary = salary;
    }

    public static void main(String[] args) {
        SalesPerson tom = new SalesPerson("Tom", new BigDecimal("400.21"), 170);
        SalesPerson bob = new SalesPerson("Bob", new BigDecimal("700.38"), 160);
        SalesPerson fry = new SalesPerson("Fry", new BigDecimal("900.57"), 220);
        Manager karry = new Manager("Karry", new BigDecimal(1000), 80);
        Manager perry = new Manager("Perry", new BigDecimal(2000), 135);
        Manager jack = new Manager("Jack", new BigDecimal(3500), 175);
        Employee[] employeesArray = {tom, bob, fry, karry, perry, jack};
        Company Valve = new Company(employeesArray);
        Valve.giveEverybodyBonus(new BigDecimal(0));
        System.out.println(jack.toPay());
        System.out.println(Valve.nameMaxSalary());
        System.out.println(Valve.totalToPay());
    }
}

class SalesPerson extends Employee {
    private final int percent;

    public SalesPerson(String name, BigDecimal salary, int percent) {
        super(name, salary);
        this.percent = percent;
    }

    @Override
    public void setBonus(BigDecimal percent) {
        if (this.percent > 200) {
            percent = percent.multiply(new BigDecimal(3));
        }
        else if (this.percent > 100) {
            percent = percent.multiply(new BigDecimal(2));
        }
        super.updateBonus(percent);
    }
}
class Manager extends Employee {
    private final int quantity;

    Manager(String name, BigDecimal salary, int clientAmount) {
        super(name, salary);
        this.quantity = clientAmount;
    }

    @Override
    public void setBonus(BigDecimal bonus) {
        if (quantity > 150) {
            bonus = bonus.add(new BigDecimal(500));
        }
        else if(quantity > 100) {
            bonus = bonus.add(new BigDecimal(1000));
        }
        super.updateBonus(bonus);
    }
}
class Company {
    private final Employee[] employees;
    Company(Employee[] employees) {
        this.employees = employees;
    }
    public void giveEverybodyBonus(BigDecimal companyBonus) {
        for (Employee employee : employees) {
            employee.updateBonus(companyBonus);
        }
    }
    public BigDecimal totalToPay() {
        BigDecimal totalSum = BigDecimal.ZERO;
        for (Employee employee : employees) {
            BigDecimal currentEmployeeSalary = employee.toPay();
            totalSum = totalSum.add(currentEmployeeSalary);
        }
        return totalSum;
    }
    public String nameMaxSalary() {
        BigDecimal maxSalary = BigDecimal.ZERO;
        String employeeWithMaxSalary = "";
        for (Employee employee : employees) {
            int tempRes = employee.toPay().compareTo(maxSalary);
            if (tempRes > 0) {
                maxSalary = employee.toPay();
                employeeWithMaxSalary = employee.getLastName();
            }
        }
        return employeeWithMaxSalary;
    }
}