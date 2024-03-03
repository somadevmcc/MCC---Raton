import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;

public class view extends JPanel  {
    
   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            Thread GUI = new Thread(() -> {
                GrafoMatriz grafoMatriz = crearMatriz();
            
            Leer leer = new Leer();
            String inicio = "";
            String[] metasplit;
            String meta = "";
            Thread raton1;
            Thread raton2;
            Graficos mapView1 = new Graficos(grafoMatriz);
                JFrame frame1 = new JFrame();
                frame1.setTitle("Algorithm Visualization - Window A");
                frame1.getContentPane().add(mapView1);
                frame1.setResizable(true);
                frame1.setSize(800, 600);
                frame1.setLocation(0, 0);
                frame1.setAlwaysOnTop(true);
            
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame1.setVisible(true);
                System.out.println("1. BFS");
                System.out.println("2. DFS");
                System.out.println("3. A*");
                System.out.println("4. Greedy");
                System.out.println("5. Raton 2 ventanas automatico");
                System.out.println("6. Raton 1 ventana automatico");
                System.out.println("7. Raton 1 ventana manual");
                System.out.print("Opcion:");
                
                int opcion = leer.leerInt("Favor de seleccionar una opcion");
                switch(opcion){
                  case 1:
                      inicio = leer.leerStringMatriz("Favor de escribir la coordenada de inicio: ejemplo 0,0",grafoMatriz);
                      meta = leer.leerStringMatriz("Favor de escribir la coordenada meta: ejemplo 0,1",grafoMatriz);
                    
                      BFS Bfs = new BFS(grafoMatriz.getNodo(Integer.parseInt(inicio.split(",")[0]), Integer.parseInt(inicio.split(",")[1])),
                      grafoMatriz.getNodo(Integer.parseInt(meta.split(",")[0]), Integer.parseInt(meta.split(",")[1])),grafoMatriz, mapView1);
                      Bfs.execute();
                      break;
                  case 2:
                     
                      inicio = leer.leerStringMatriz("Favor de escribir la coordenada de inicio: ejemplo 0,0",grafoMatriz);
                      meta = leer.leerStringMatriz("Favor de escribir la coordenada meta: ejemplo 0,1",grafoMatriz);
                      DFS Dfs = new DFS(grafoMatriz.getNodo(Integer.parseInt(inicio.split(",")[0]), Integer.parseInt(inicio.split(",")[1])),
                      grafoMatriz.getNodo(Integer.parseInt(meta.split(",")[0]), Integer.parseInt(meta.split(",")[1])),grafoMatriz, mapView1);
                      Dfs.execute();
                      break;
                  case 3:
                     
                      inicio = leer.leerStringMatriz("Favor de escribir la coordenada de inicio: ejemplo 0,0",grafoMatriz);
                      meta = leer.leerStringMatriz("Favor de escribir la coordenada meta: ejemplo 0,1",grafoMatriz);
                      aEstrella estrella = new aEstrella(grafoMatriz.getNodo(Integer.parseInt(inicio.split(",")[0]), Integer.parseInt(inicio.split(",")[1])),
                      grafoMatriz.getNodo(Integer.parseInt(meta.split(",")[0]), Integer.parseInt(meta.split(",")[1])),grafoMatriz, mapView1);
                      estrella.execute();
                      break;
                  case 4:
                      
                      inicio = leer.leerStringMatriz("Favor de escribir la coordenada de inicio: ejemplo 0,0",grafoMatriz);
                      meta = leer.leerStringMatriz("Favor de escribir la coordenada meta: ejemplo 0,1",grafoMatriz);
                      Greedy greedy1 = new Greedy(grafoMatriz.getNodo(Integer.parseInt(inicio.split(",")[0]), Integer.parseInt(inicio.split(",")[1])),
                      grafoMatriz.getNodo(Integer.parseInt(meta.split(",")[0]), Integer.parseInt(meta.split(",")[1])), grafoMatriz, mapView1);
                      greedy1.execute();


                      break;
                  case 5:
                     
                      
                      GrafoMatriz grafoMatrizB = new GrafoMatriz(grafoMatriz);
                      
                      Stack<Nodo> F1 = new Stack<>();
      
                      Graficos mapView2 = new Graficos(grafoMatrizB);
                      JFrame frame2 = new JFrame();
                      frame2.setTitle("Algorithm Visualization - Window B");
                      frame2.getContentPane().add(mapView2);
                      frame2.setResizable(true);
                      frame2.setSize(800, 600);
                      frame2.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame2.getWidth(), 0);
                      frame2.setAlwaysOnTop(true);
                      frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                      frame2.setVisible(true);
      
                      
                      aEstrella estrella1 = new aEstrella(grafoMatriz.nodoInicial(), grafoMatriz.nodoCentral(), grafoMatriz, mapView1);
                      estrella1.execute();
                      
      
                      aEstrella estrella2 = new aEstrella(grafoMatrizB.nodoFinal(), grafoMatrizB.nodoCentral(), grafoMatrizB, mapView2);            estrella2.execute();
                      estrella2.execute();
                      break;
                  case 6:
                     
                      raton1 = new Thread(() -> {
                          
                          aEstrella ratonA = new aEstrella(grafoMatriz.nodoInicial(), grafoMatriz.nodoCentral(), grafoMatriz, mapView1);
                          ratonA.execute();
                      });
                      raton2 = new Thread(() -> {
                          
                          aEstrella ratonB = new aEstrella(grafoMatriz.nodoFinal(), grafoMatriz.nodoCentral(), grafoMatriz, mapView1);
                          ratonB.execute();
                      });
                      raton1.start();
                      raton2.start();
                      break;
                    case 7:
                        String inicio1 = leer.leerStringMatriz("Favor de escribir la coordenada de inicio del Raton 1: ejemplo 0,0",grafoMatriz);
                        String inicio2 = leer.leerStringMatriz("Favor de escribir la coordenada de inicio del Raton 2: ejemplo 0,0",grafoMatriz);
                        String meta1 = leer.leerStringMatriz("Favor de escribir la coordenada meta: ejemplo 0,1",grafoMatriz);
                       // String meta1 = "10,10";
                       // String inicio2 = "29,15";
                        raton1 = new Thread(() -> {
                            
                            aEstrella ratonA = new aEstrella(grafoMatriz.getNodo(Integer.parseInt(inicio1.split(",")[0]), Integer.parseInt(inicio1.split(",")[1])),
                            grafoMatriz.getNodo(Integer.parseInt(meta1.split(",")[0]), Integer.parseInt(meta1.split(",")[1])), grafoMatriz, mapView1);
                           // aEstrella ratonA = new aEstrella(grafoMatriz.nodoInicial(), grafoMatriz.nodoCentral(), grafoMatriz, mapView1);
                            ratonA.execute();
                        });
                        raton2 = new Thread(() -> {
                            
                            aEstrella ratonB = new aEstrella(grafoMatriz.getNodo(Integer.parseInt(inicio2.split(",")[0]), Integer.parseInt(inicio2.split(",")[1])),
                            grafoMatriz.getNodo(Integer.parseInt(meta1.split(",")[0]), Integer.parseInt(meta1.split(",")[1])), grafoMatriz, mapView1);

                           // aEstrella ratonB = new aEstrella(grafoMatriz.getNodo(Integer.parseInt(inicio2.split(",")[0]), Integer.parseInt(inicio2.split(",")[1])), grafoMatriz.nodoCentral(), grafoMatriz, mapView1);
                            ratonB.execute();
                        });
                        raton1.start();
                        raton2.start();
                        break;
                  default:
                      System.out.println("Opcion no valida");
      
                }
            });
            GUI.start();
            

          

           
           

    
         

        
        });
    }

    public static GrafoMatriz crearMatriz(){
        Leer leer = new Leer();
        
        int filas = leer.leerInt("Numero de filas:");
        int columnas = leer.leerInt("Numero de columnas:");
        //*/
        //int filas = 20;
        //int columnas = 20;
  
  
        int porcentaje = leer.leerIntMatriz("Numero de porcentaje:");
        //int porcentaje = 20;
        GrafoMatriz grafo = new GrafoMatriz (filas ,columnas, porcentaje);
        return grafo;
        
      }
}
