package burbujas;

public class Pregunta {
    public int operacion;
    public String opc1, opc2, opc3, opc4;
    public String rta;
    public Pregunta(int operacion, String opc1, String opc2, String opc3, String opc4, String rta) {
        this.operacion = operacion;
        this.opc1 = opc1;
        this.opc2 = opc2;
        this.opc3 = opc3;
        this.opc4 = opc4;
        this.rta = rta;
    }
}
