import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.SwingWorker;

import java.util.Collections;

public class DFS extends SwingWorker<Void, Nodo> {
    private Graficos view;
    private Nodo goal;
    private Nodo inicio;
    private GrafoMatriz grafo;
    private ArrayList<String> blackList;
    private Stack<Nodo> optimalPath;

    public DFS(Nodo inicio,Nodo goal, GrafoMatriz grafo, Graficos view) {
        this.view = view;
        this.goal = goal;
        this.grafo = grafo;
        this.inicio = inicio;
        this.blackList = new ArrayList<String>();
        this.optimalPath = new Stack<Nodo>();
    }

    @Override
    protected Void doInBackground() throws Exception {
        Stack<Nodo> F = new Stack<>();
        F.push(this.inicio);
        DFS(F);
        return null;
    }

    @Override
    protected void process(List<Nodo> chunks) {
        // Update GUI with the latest changes
        view.updateGUI();
    }

    public void DFS(Stack<Nodo> F) {
        if (F.isEmpty()) {
            System.out.println("Solucion no encontrada");
            return;
        } else {
            Nodo EA = F.pop();
            EA.setEstado(Nodo.Estado.ABIERTO);
            grafo.setEstado(EA);
            blackList.add(""+EA.getPosX()+""+EA.getPosY());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (goalTest(EA)) {
                markOptimalPath(EA);
                System.out.println("Goal reached!");
                publish();
            } else {
            publish();
            Stack<Nodo> OS = expand(EA);
            Append(F,OS);
            DFS(F);
            }
        }
    }
    private void markOptimalPath(Nodo goalNode) {
        // Clear previous path
        while (!optimalPath.isEmpty()) {
            Nodo node = optimalPath.pop();
            node.setEstado(Nodo.Estado.CERRADO);
            grafo.setEstado(node);
        }
    
        // Mark the start node
        grafo.getNodo(inicio.getPosX(), inicio.getPosY()).setEstado(Nodo.Estado.INICIO);
    
        // Mark the goal node
        goalNode.setEstado(Nodo.Estado.META);
        optimalPath.push(goalNode);
    
        // Uncomment the line below to trigger GUI update
        publish();
    }
    private void Append(Stack<Nodo> F, Stack<Nodo> OS){
        while(!OS.isEmpty()){
            F.push(OS.pop());
        }
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
        optimalPath.push(EA);
        return OS;
    }

    private boolean goalTest(Nodo EA) {
        
        return (EA).equals(this.goal);
    }
}
