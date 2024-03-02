public class Nodo {
    private String nombre;
    private double distancia;
    private int posicion_x;
    private int posicion_y;
    private boolean bloqueado;
    public static enum Estado { NOVISITADO, ABIERTO, CERRADO,META,INICIO };

    private Estado estado = Estado.NOVISITADO;
    private double heuristic; // heuristic
    private Nodo backPathNode;
    private int id;
    
    private double F;
    


    public Nodo(String nombre, int distancia) {
        this.distancia = distancia;
        this.nombre = nombre;
        this.bloqueado = false;
        this.F = 0;
        this.id = 1;
    }
    public Nodo(String nombre, boolean bloqueado) {
        this.distancia = 0;
        this.nombre = nombre;
        this.bloqueado = bloqueado;
        this.id = 1;
    }
    public Nodo(String nombre, double distancia, boolean bloqueado, int id) {
        this.distancia = distancia;
        this.nombre = nombre;
        this.bloqueado = bloqueado;
        this.id = id;
    }
    public void setF(double F){
        this.F = F;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getHeuristic() {
        return heuristic;
    }

    
    public void setHeuristic(double h) {
        this.heuristic = h;
    }
    public Nodo getBackPathNode() {
        return backPathNode;
    }
    
    // f(n) = g(n) + h(n) -> cost + heuristic
    public double getF() {
        return distancia + heuristic;
    }

    public void setBackPathNode(Nodo backPathNode) {
        this.backPathNode = backPathNode;
    }
    public void setPosicionX(int x){
        this.posicion_x = x;
    }
    public void setPosicionY(int y){
        this.posicion_y = y;
    }

    public int getPosX(){
        return posicion_x;
    }
    public int getPosY(){
        return posicion_y;
    }

    public void setPosX(int x){
        this.posicion_x = x;
    }
    public void setPosY(int y){
        this.posicion_y = y;
    }

    public String getNombre(){
        return this.nombre;
    }
    public Double getDistancia(){
        return this.distancia;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setDistancia(Double distancia){
        this.distancia = distancia;
    }

    public boolean getBloqueado(){
        return this.bloqueado;
    }

    public void setBloqueado(boolean bloqueado){
        this.bloqueado = bloqueado;
    }
    @Override
    public String toString() {

        String respuesta = "";
        if(getBloqueado()){
            return " XX";
        }else{
            //return " "+nombre+" ";
            return " "+posicion_x+""+posicion_y;
        }
        //return "'" + nombre + '\'' + ", distancia=" + distancia ;
    }

    public String toString(boolean flag) {

        String respuesta = "";
        if(getBloqueado()){
            return " XX";
        }else{
            //return " "+nombre+" ";
            switch(this.getEstado().toString()){
                case "NOVISITADO":
                    respuesta = " "+posicion_x+""+posicion_y;
                break;
                case "ABIERTO":
                    respuesta = " AB";
                break;
                case "CERRADO":
                    respuesta = " CE";
                break;
            }
            return respuesta;
        }
        //return "'" + nombre + '\'' + ", distancia=" + distancia ;
    }

    

}


