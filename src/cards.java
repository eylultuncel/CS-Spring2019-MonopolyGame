public class cards extends square {
    String content;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public cards(int id){
        super(id);
    }

    public int getId() {
        return super.getId();
    }
    public void setId(int id) {
        super.setId(id);
    }

}
