public class tax extends square {

    public tax(int id){
        super(id);
    }

    public int getId() {
        return super.getId();
    }
    public void setId(int id) {
        if (id != 4 || id != 38) {
            return;
        }
        super.setId(id);
    }
}
