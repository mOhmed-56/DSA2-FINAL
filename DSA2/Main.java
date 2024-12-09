public class Main {
    public static void main(String[] args) {
        Hash p = new Hash(1009);
        p.insert("Hazem", "010");
        p.insert("Karim", "011");
        p.insert("Seif", "012");
        p.insert("Fathy", "013");
        p.insert("Sa3d", "014");
        p.insert("Gehad", "016");

        p.update("Gehad", "015");
        p.remove("Hazem");
        p.search("Karim");
        p.insert("Hazem", "020");



        p.print();



    }
}