import java.util.Stack;

import javax.swing.SwingWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Greedy extends SwingWorker<Void, Nodo>{
    private Nodo goal;
    private Nodo inicio;
    private GrafoMatriz grafo;
    private ArrayList<String> blackList;
    private Stack<Nodo> optimalPath;
    private Graficos view;

    public Greedy(Nodo inicio, Nodo goal, GrafoMatriz grafo, Graficos view) {
        this.inicio = inicio;
        this.goal = goal;
        this.grafo = grafo;
        this.blackList = new ArrayList<String>();
        this.optimalPath = new Stack<Nodo>();
        this.view = view;
    }

    @Override
    protected Void doInBackground() throws Exception {
        Stack<Nodo> F = new Stack<>();
        F.push(this.inicio);
        GBFS(F);
        return null;
    }

    @Override
    protected void process(List<Nodo> chunks) {
        // Update GUI with the latest changes
        view.updateGUI();
    }
    public void GBFS(Stack<Nodo> F) {
        if (F.empty()) {
            System.out.println("Solucion no encontrada");
            return;
            
        } else {
            Nodo EA = F.pop();
            EA.setEstado(Nodo.Estado.ABIERTO);
            grafo.setEstado(EA);
            optimalPath.push(EA);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (goalTest(EA)) {
                System.out.println("Goal reached!");
                EA.setEstado(Nodo.Estado.META);
                publish();
            } else {
            publish();
            Stack<Nodo> OS = expand(EA);
            evaluate(OS);
            Append(F,OS);
            Sort(F);
            GBFS(F);
            }
        }
    }
    private void Sort(Stack<Nodo> F) {
        F.sort((node1, node2) -> Double.compare(node2.getF(), node1.getF()));
        
      
    }

    private void Append(Stack<Nodo> F, Stack<Nodo> OS) {
        while (!OS.isEmpty()) {
            F.push(OS.pop());
        }
    }
    private void evaluate(Stack<Nodo> OS) {
        double distanciaAFinal,distanciaAInicio = 0;
        ArrayList<Nodo> OSList = new ArrayList<>();
        int index = OS.size();
        for( int i = 0;i<index;i++){
            Nodo nodoVecino = OS.pop();
            distanciaAFinal = calcularDistancias(goal, nodoVecino);
            nodoVecino.setHeuristic(distanciaAFinal);
            
            nodoVecino.setF(distanciaAInicio + distanciaAFinal);

            nodoVecino.setEstado(Nodo.Estado.ABIERTO);
            grafo.setEstado(nodoVecino);

            OS.add(0,nodoVecino);
        }

    }
    public double calcularDistancias(Nodo meta, Nodo actual){

        int dx = meta.getPosX() - actual.getPosX();
        int dy = meta.getPosY() - actual.getPosY();

        //return Math.sqrt(dx * dx + dy * dy);
        //return Math.sqrt(Math.pow(actual.getPosX()-meta.getPosX(),2)+Math.pow(actual.getPosY()-meta.getPosY(),2));
        return Math.sqrt(Math.pow(actual.getPosX()-meta.getPosX(),2)+Math.pow(actual.getPosY()-meta.getPosY(),2));
    }
    private Stack<Nodo> expand(Nodo EA){
        Stack<Nodo> OS = new Stack<Nodo>();
        Nodo nodoVecino = null;
        String coordenadasX = "";
        String coordenadasY = "";
        for(int i=-1;i<=1;i+=2){
          
            try{
                
                nodoVecino = this.grafo.getNodo(EA.getPosX()+i, EA.getPosY());
                coordenadasX = nodoVecino.getPosX()+""+nodoVecino.getPosY();
                if(!nodoVecino.getBloqueado() && !blackList.contains( coordenadasX) && nodoVecino.getEstado().equals(Nodo.Estado.NOVISITADO) ) {
                    OS.add(nodoVecino);
                    
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }
        for(int i=-1;i<=1;i+=2){
            try{
                nodoVecino = this.grafo.getNodo(EA.getPosX(), EA.getPosY()+i);
                coordenadasY = nodoVecino.getPosX()+""+nodoVecino.getPosY();
                if(!nodoVecino.getBloqueado() && !blackList.contains(  coordenadasY) && nodoVecino.getEstado().equals(Nodo.Estado.NOVISITADO)){
                    OS.add(nodoVecino);
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }
        return OS;
    }

    private boolean goalTest(Nodo EA) {
        
        return (EA).equals(this.goal);
    }
}
