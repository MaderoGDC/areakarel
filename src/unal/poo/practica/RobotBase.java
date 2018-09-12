//AREA DE LOS LOTES KAREL
package unal.poo.practica;

import becker.robots.*;

public class RobotBase {

    public static City objetos;
    public static Robot estudiante;

    static int maxAv;
    static int minAv;
    static int maxStreet;
    static int minStreet;
    static int vertical;
    static int lateral;
    static int matriz[][];
    static int area = 0;

    //MAIN  
    public static void main(String[] args) {
        //Declarar la creacion de la ciudad
        objetos = new City("Field.txt");
        objetos.showThingCounts(true);

        //Definicion de la ubicacion del robot, Ciudad, posicion, Direccion, Numero things en el bolso.
        estudiante = new Robot(objetos, 3, 1, Direction.WEST, 15);
        minStreet = estudiante.getAvenue();
        minAv = estudiante.getAvenue();
        maxStreet = estudiante.getStreet();
        maxAv = estudiante.getStreet();

        rectangulo();

        while (estudiante.getDirection() != Direction.WEST) {
            girar(1);
        }
        mover(1);
        //en los positivos
        vertical = (maxStreet - minStreet) + 1;
        lateral = (maxAv - minAv) + 1;
        area();
        contar();
        System.out.println("el area de la figura es: " + area );

    }

//INICIO DECLARACION DE FUNCIONES.
//mover x pasos    
    public static void mover(int pasos) {
        for (int i = pasos; i > 0; i--) {
            estudiante.move();
        }
    }
//dar x giros

    public static void girar(int giros) {
        for (int i = giros; i > 0; i--) {
            estudiante.turnLeft();
        }
    }

    //giros
    public static void giroDerecha() {
        girar(3);
    }

    public static void giroIzquierda() {
        girar(1);
    }

    public static void delante() {
        mover(1);
    }

//recorrer la figura
    public static void rectangulo() {
        //recorre la figura siguiendo la ley de la mano derecha.
        while (estudiante.canPickThing() == false) {
            //siempre hace conteo para establecer los limites laterales y veticales de la figura
            //menor lateral
            if (estudiante.getAvenue() <= minAv) {
                minAv = estudiante.getAvenue();
            }
            //maximo lateral
            if (estudiante.getAvenue() >= maxAv) {
                maxAv = estudiante.getAvenue();
            }
            //menor vertical
            if (estudiante.getStreet() <= minStreet) {
                minStreet = estudiante.getStreet();
            }
            //mayor vertical
            if (estudiante.getStreet() >= maxStreet) {
                maxStreet = estudiante.getStreet();
            }

            giroDerecha();
            if (estudiante.frontIsClear() == true) {
                mover(1);
                continue;
            }
            giroIzquierda();
            if (estudiante.frontIsClear() == true) {
                mover(1);
                continue;
            }
            giroIzquierda();
            if (estudiante.frontIsClear() == true) {
                mover(1);
                continue;
            }
            giroIzquierda();
            mover(1);

        }

    }

//Area final
    public static int area() {
        //crear matriz y variable que guarda area
        matriz = new int[vertical][lateral];
        //recorrer matriz y llenarla de 5
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < lateral; j++) {
                matriz[i][j] = 5;

            }
        }
        //vuelve a recorrer la figura y empieza a delimitar con unos 
        while (estudiante.canPickThing() == false) {
            
            matriz[estudiante.getStreet()- minStreet][estudiante.getAvenue()- minAv]=0;
            poner_1();
            giroDerecha();
            poner_1();

            //vuelve a recorrer la figura  
            if (estudiante.frontIsClear() == true) {
                matriz[estudiante.getStreet()- minStreet][estudiante.getAvenue()- minAv]=0;
                poner_1();
                mover(1);
                continue;
            }
            giroIzquierda();
            if (estudiante.frontIsClear() == true) {
                matriz[estudiante.getStreet()- minStreet][estudiante.getAvenue()- minAv]=0;
                poner_1();
                mover(1);
                continue;
            }
            giroIzquierda();
            if (estudiante.frontIsClear() == true) {
                matriz[estudiante.getStreet()- minStreet][estudiante.getAvenue()- minAv]=0;
                poner_1();
                mover(1);
                continue;
            }
            matriz[estudiante.getStreet()- minStreet][estudiante.getAvenue()- minAv]=0;
            poner_1();
            giroIzquierda();
            mover(1);            
        }
        //imprimir matriz
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < lateral; j++) {
                System.out.print(matriz[i][j] + " ");;
            }
            System.out.println();
        }

        return area;
    }

    public static void poner_1() {
        if (estudiante.frontIsClear() == false) {
            if (estudiante.getDirection() == Direction.EAST) {
                matriz[(estudiante.getStreet() - minStreet)][(estudiante.getAvenue() - minAv) + 1] = 1;
            }
            if (estudiante.getDirection() == Direction.WEST) {
                matriz[(estudiante.getStreet() - minStreet)][(estudiante.getAvenue() - minAv) - 1] = 1;
            }
        }
    }
//contar los unos de la figura
    public static void contar() {
        boolean cuento = false;
        for (int i = 0; i < vertical; i++) {
            int subarea=0;
            for (int j = 0; j < lateral; j++) {
                if (matriz[i][j] == 1 ) {
                    if(cuento){
                        area+=subarea; 
                        subarea=0;
                    }
                    area++;
                    cuento = !cuento;
                }
                if (cuento && matriz[i][j] == 5) {
                    subarea++;
                }
                if (cuento && matriz[i][j] == 0) {
                    cuento = !cuento;
                }
                System.out.println("y"+ i +" " + "x: " + j+ "area: "+ area);
            }            
        }

    }

}

//            //verificar
//            System.out.println("pos x: " + estudiante.getAvenue() + "pos y: " + estudiante.getStreet());
//            System.out.println("minX: " + minAv + "minY " + minStreet);
//            System.out.println("maxX: " + maxAv + "maxY " + maxStreet);
//            System.out.println("X: " + lateral + "Y: " + vertical);
