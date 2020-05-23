package models;

public class Guide {
    private int id;
    private String title;
    private String description;
    private String example;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public boolean isEqual(Guide obj) {
        if(this.getId() != obj.getId()) {
            return false;
        }
        if(!this.getTitle().equals(obj.getTitle())) {
            return false;
        }
        if(!this.getDescription().equals(obj.getDescription())) {
            return false;
        }
        if(!this.getExample().equals(obj.getExample())) {
            return false;
        }
        return true;
    }
}
