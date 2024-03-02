import java.util.Stack;

import javax.swing.SwingWorker;
import javax.swing.text.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class aEstrella extends SwingWorker<Void, Nodo>{
    private Graficos view;
    private Nodo goal;
    private Nodo inicio;
    private GrafoMatriz grafo;
    private GrafoMatriz grafoInterno;
    private ArrayList<String> blackList;
    private Stack<Nodo> optimalPath;
    long startTime;

    
    public aEstrella(Nodo inicio, Nodo goal, GrafoMatriz grafo, Graficos view) {
        this.inicio = inicio;
        this.goal = goal;
        this.grafo = grafo;
        this.blackList = new ArrayList<String>();
        this.optimalPath = new Stack<Nodo>();
        this.view = view;
        this.grafoInterno = new GrafoMatriz(grafo);
        startTime = System.currentTimeMillis();
       
    }
    
    @Override
    protected Void doInBackground() throws Exception {
        Stack<Nodo> F = new Stack<>();
        F.push(this.inicio);
        AEstrella(F);
        return null;
    }

    @Override
    protected void process(List<Nodo> chunks) {
        // Update GUI with the latest changes
        view.updateGUI();
    }
    public void AEstrella(Stack<Nodo> F) {


        if (F.size() == 0) {
            return;
        } else {
            Nodo EA = F.pop();
            EA.setEstado(Nodo.Estado.ABIERTO);
            grafo.setEstado(EA);
            grafoInterno.setEstado(EA);
            
           
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if (goalTest(EA)) {
                optimalPath = filterDirectPath(optimalPath);
                marcarCamino(EA);
                EA.setEstado(Nodo.Estado.META);
                grafo.setEstado(EA);
                double tiempoFinal = (System.currentTimeMillis() - startTime) / 1000.0;
                System.out.println("Goal reached! Inicio:"+ this.inicio +" Meta:"+this.goal+" Tiempo: " + tiempoFinal + " segs");
                
            } else {
            publish();
            Stack<Nodo> OS = expand(EA);
            evaluate(OS);
            Append(F,OS);
            Sort(F);
            AEstrella(F);
            
            
            }
        }
    }
    private void Append(Stack<Nodo> F, Stack<Nodo> OS) {
        while (!OS.isEmpty()) {
            F.push(OS.pop());
        }
    }
    private void Sort(Stack<Nodo> F) {
        F.sort((node1, node2) -> Double.compare(node2.getF(), node1.getF()));
        
       // Collections.sort(F, Collections.reverseOrder()); 
    }

    private void marcarCamino(Nodo goalNode) {
    
        while (!optimalPath.isEmpty()) {
            Nodo node = optimalPath.pop();
            node.setEstado(Nodo.Estado.CERRADO);
            grafo.setEstado(node);
            grafoInterno.setEstado(node);
        }
        optimalPath.push(goalNode);
    
        publish();
    }
    private Stack<Nodo> filterDirectPath(Stack<Nodo> originalPath) {
        Stack<Nodo> filteredPath = new Stack<>();
        
        // Add the goal node to the filtered path
        filteredPath.push(originalPath.pop());
    
        // Iterate over the original path to filter out unnecessary steps
        while (!originalPath.isEmpty()) {
            Nodo currentNode = originalPath.pop();
            Nodo nextNode = filteredPath.peek();
    
            // Check if the nodes are in the top, bottom, left, or right direction
            if (isAdjacent(currentNode, nextNode)) {
                filteredPath.push(currentNode);
            }
        }
    
        // Reverse the stack to get the correct order
        Collections.reverse(filteredPath);
    
        return filteredPath;
    }
    
    private boolean isAdjacent(Nodo node1, Nodo node2) {
        int xDiff = Math.abs(node1.getPosX() - node2.getPosX());
        int yDiff = Math.abs(node1.getPosY() - node2.getPosY());
        return (xDiff == 0 && yDiff == 1) || (xDiff == 1 && yDiff == 0);
    }
    private Stack<Nodo> expand(Nodo EA){
        Stack<Nodo> OS = new Stack<Nodo>();
        Nodo nodoVecino = null;
        String coordenadasX = "";
        String coordenadasY = "";
        for(int i=-1;i<=1;i+=2){
          
            try{
                
                nodoVecino = this.grafoInterno.getNodo(EA.getPosX()+i, EA.getPosY());
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
                nodoVecino = this.grafoInterno.getNodo(EA.getPosX(), EA.getPosY()+i);
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
    
    public double getDistanciaRecorrida(Nodo meta, Nodo actual){

        return Math.sqrt(Math.pow(actual.getPosX()-meta.getPosX(),2)+Math.pow(actual.getPosY()-meta.getPosY(),2));
    }
    private double calcularDistancias(Nodo meta, Nodo actual){
        return Math.abs(actual.getPosX()-meta.getPosX())+Math.abs(actual.getPosY()-meta.getPosY());
    }
    private boolean goalTest(Nodo EA) {
        
        return (EA.getNombre()).equals(this.goal.getNombre());
    }
    
    private void evaluate( Stack<Nodo> OS) {
        double distanciaAFinal,distanciaAInicio = 0;
        ArrayList<Nodo> OSList = new ArrayList<>();
        int index = OS.size();
        for( int i = 0;i<index;i++){
            Nodo nodoVecino = OS.pop();
            distanciaAInicio = getDistanciaRecorrida(inicio, nodoVecino);
            nodoVecino.setDistancia(distanciaAInicio);
            distanciaAFinal = calcularDistancias(goal, nodoVecino);
            nodoVecino.setHeuristic(distanciaAFinal);
            
            nodoVecino.setF(distanciaAInicio + distanciaAFinal);

            nodoVecino.setEstado(Nodo.Estado.ABIERTO);
            grafo.setEstado(nodoVecino);

            OS.add(0,nodoVecino);
        }/*/
        while(!OS.empty()){
            Nodo nodoVecino = OS.pop();
            distanciaAInicio = getDistanciaRecorrida(inicio, nodoVecino);
            nodoVecino.setDistancia(distanciaAInicio);
            distanciaAFinal = calcularDistancias(goal, nodoVecino);
            nodoVecino.setHeuristic(distanciaAFinal);
            
            nodoVecino.setF(distanciaAInicio + distanciaAFinal);

            nodoVecino.setEstado(Nodo.Estado.ABIERTO);
            grafo.setEstado(nodoVecino);

            OS.add(0,nodoVecino);
        }
        /*
        while(!F.empty()){
            OSList.add(F.pop());
        }
        Collections.sort(OSList, (node1, node2) -> Double.compare(node1.getF(), node2.getF()));
        
        for (Nodo nodo : OSList) {
            F.add(0,nodo);
        }
        */
        //F.add(0,OSList.get(0));

    }
}
