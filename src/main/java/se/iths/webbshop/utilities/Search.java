package se.iths.webbshop.utilities;

public class Search {
    private String category;
    private String query;
    private String tags;

    public Search() {
        this.category = "orders";
        this.query = "";
        this.tags = "";
    }

    public Search(String category, String query, String tags) {
        this.category = category;
        this.query = query;
        this.tags = tags;
    }

    public String getCategory() {
        return category == null ? "" : category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuery() {
        return query == null ? "" : query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getTags() {
        return tags == null ? "" : tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Search{" +
                "category='" + category + '\'' +
                ", query='" + query + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
