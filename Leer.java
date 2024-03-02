import java.util.Scanner;
public class Leer {
    private Scanner scan;

    public Leer(){
        this.scan = new Scanner(System.in);
    }

    public int leerReglas(String mensaje){
        String valor = "";
        boolean flagValido = true;
        while(flagValido) {
        	System.out.println(mensaje);
        	valor = scan.nextLine();
        	if(esNumerico(valor)){
        		flagValido = false;
            }else {
            	System.out.println("Favor de introducir solo numeros");
            }
        }
        
        return Integer.parseInt(valor);
        

    }

    public String leerString(String mensaje){
        System.out.println(mensaje);
        String valor = scan.nextLine();
        return valor;
    }

    public String leerStringMatriz(String mensaje, GrafoMatriz grafo){

        while (true) {
            try {
                System.out.println(mensaje);
                String valor = scan.nextLine();
                String[] valorsplit = valor.split(",");

                if(valorsplit.length > 2 || !esNumerico(valorsplit[0]) || !esNumerico(valorsplit[1])) {
                    System.out.println("Valor invalido favor de verificar");
                    continue;
                }
                Nodo[][] matriz = grafo.getMatriz();
                boolean bloqueado = matriz[Integer.parseInt(valorsplit[0])][Integer.parseInt(valorsplit[1])].getBloqueado();
                if(bloqueado){
                    System.out.println("Nodo bloqueado favor de verificar");
                    continue;
                }
                return valor;
            } catch (Exception e) {
                System.out.println("Valor invalido favor de verificar");
            }
        }
        
    }
    
    public int leerReglas(String mensaje,int minimo,int maximo){
        String valor = "";
        int res      = 0;
        boolean flagValido = true;
        while(flagValido) {
        	System.out.println(mensaje);
        	valor = scan.nextLine();
        	if(esNumerico(valor)){
        		res = Integer.parseInt(valor);
        		if (res >= minimo && res <= maximo) {
        			flagValido = false;
        		}else {
        			System.out.println("Favor de escribir en un rango entre "+minimo+" y "+maximo);
        		}
        		
            }else {
            	System.out.println("Favor escribir solo numeros");
            }
        }
        
        return res;
    }

     public String leerCoordenas(String mensaje,int filas,int columnas){
        String valor = "";
        int var1     = 0;
        int var2     = 0;
        boolean flagValido = true;
        int commaCount = 0;
        
        while(flagValido) {
        	System.out.println(mensaje);
        	valor = scan.nextLine();

            for (int i = 0; i < valor.length(); i++) {
                if (valor.charAt(i) == ',') {
                    commaCount++;
                }
            }
            if (commaCount == 1 ){
                String[] parts = valor.split(",");
                if(parts.length == 2){
                    

                    if(esNumerico(parts[0]) && esNumerico(parts[1])){
                    var1 = Integer.parseInt(parts[0]);
                    var2 = Integer.parseInt(parts[1]);
                    if (var1 >=0 && var2 >=0 && var1 <= filas && var2 <= columnas) {
                        flagValido = false;
                    }
                    
                    }else {
                        System.out.println("Favor escribir solo numeros");
                    }
                }
                
            }
            if(flagValido){
                System.out.println("Valor invalido");
            }
            commaCount = 0;
        	
        }
        
        return valor;
    }
    public int leerInt(String mensaje){
        String valor = "";
        int res      = 0;
        boolean flagTerminar = true;
        
            System.out.println(mensaje);
            valor = scan.nextLine();
            if(esNumerico(valor)){
                res = Integer.parseInt(valor);
                flagTerminar = false;
            }else{
                System.out.println("Favor de escribir solo valores numericos");
            }
        
        
        return res;

    }

    public int leerIntMatriz(String mensaje){
        String valor = "";
        int res      = 0;
        boolean flagTerminar = true;
        while (flagTerminar) {
            System.out.println(mensaje);
            valor = scan.nextLine();
            if(esNumerico(valor) ){
                res = Integer.parseInt(valor);
                if(res > 0 && res <= 100 ){
                    flagTerminar = false;
                }
            }else{
                System.out.println("Favor de escribir solo valores numericos");
            }
        }
        
        return res;

    }

    public float leerFloat(String mensaje){
        String valor = scan.nextLine();
        float res      = 0;
        boolean flagTerminar = true;
        while (flagTerminar) {
            System.out.println(mensaje);
            if(esReal(valor)){
                res = Float.parseFloat(valor);
                flagTerminar = false;
            }else{
                System.out.println("Favor de escribir solo valores numericos");
            }
        }
        
        return res;

    }

    public int leerInt(){
        String valor = scan.nextLine();
        int res      = 0;
        if(esNumerico(valor)){
            
            return Integer.parseInt(valor);
        }
        return -1;

    }
    


    private static boolean esNumerico(String cad) {
		if (cad == null) {
			return false;
		}
		try {
			int numero = Integer.parseInt(cad); 
		}catch (NumberFormatException x) {
			return false;
		}
		return true;
	}

    private boolean esReal(String cad) {
        if (cad == null) {

            return false;
        }
        try {
            float numero = Float.parseFloat(cad);
        }catch (NumberFormatException x) {
            return false;
        }
        return true;
    }
    
}
