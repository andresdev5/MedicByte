/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.model;
import java.util.Scanner;
import java.util.Timer;
/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class Date {
    private int day;
    private int month;
    private int year;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getAge() {
        return year;
    }

    public void setAge(int age) {
        this.year = age;
    }
    
    public void validateDate(int day, int month, int year){
        //TODO 
        Scanner enterData = new Scanner(System.in);
        System.out.print("Ingrese su dia de Nacimiento: ");
        day = enterData.nextInt();
        System.out.print("Ingrese su mes de Nacimiento: ");
        month = enterData.nextInt();
        System.out.print("Ingrese su año de Nacimiento: ");
        year = enterData.nextInt();
        while(day<=0 || (month<=0 || month>12) || (year<=0 || year>=2020)){
            if(day<=0){
                System.out.print("El dia Ingresado es Incorrecto"
                        + "\nIngrese nuevamente: ");
                day = enterData.nextInt();
            }else{
                
            }if((month<=0 || month>12)){
                System.out.print("El mes Ingresado es Incorrecto"
                        + "\nIngrese nuevamente: ");
                month = enterData.nextInt();
            }else{
                
            }if((year<=0 || year>=2020)){
                System.out.print("El Año Ingresado es Incorrecto"
                        + "\nIngrese nuevamente: ");
                year = enterData.nextInt();
            }else{
                
            }
        }
        System.out.println("Fecha Correcta");
        
        
    }
}
