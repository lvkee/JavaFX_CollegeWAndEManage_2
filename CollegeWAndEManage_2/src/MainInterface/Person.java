package MainInterface;

public class Person {
    private String id;
    private String name;
    private String sex;
    private int age;
    private String department;
    private double waterConsumption;
    private double electricityConsumption;
    private double waterCost;
    private double electricityCost;
    private String isPay;

    public Person() {
    }

    public Person(String id, String name, String sex, int age, String department, double waterConsumption, double electricityConsumption, double waterCost, double electricityCost, String isPay) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.department = department;
        this.waterConsumption = waterConsumption;
        this.electricityConsumption = electricityConsumption;
        this.waterCost = waterCost;
        this.electricityCost = electricityCost;
        this.isPay = isPay;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setWaterConsumption(double waterConsumption) {
        this.waterConsumption = waterConsumption;
    }

    public double getWaterConsumption() {
        return waterConsumption;
    }

    public void setElectricityConsumption(double electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }

    public double getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setWaterCost(double waterCost) {
        this.waterCost = waterCost;
    }

    public double getWaterCost() {
        return waterCost;
    }

    public void setElectricityCost(double electricityCost) {
        this.electricityCost = electricityCost;
    }

    public double getElectricityCost() {
        return electricityCost;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getIsPay() {
        return isPay;
    }
}