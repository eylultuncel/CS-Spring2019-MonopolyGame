public class chanceCards extends cards {
    private String content;


    public chanceCards(int id){
        super(id);
    }

    public chanceCards(int id,String content){
        super(id);
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
