public class go extends square {

    public go(int id){
        super(id);
    }

    public int getId() {
        return super.getId();
    }
    public void setId(int id) {
        if(id!=0){
            return;
        }
        super.setId(id);
    }
}
