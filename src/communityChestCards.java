public class communityChestCards extends cards {
    private String content;

    public communityChestCards(int id){
        super(id);
    }

    public communityChestCards(int id,String content){
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
