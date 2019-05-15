package android.project.handmedown.Fooddata;

/**
 * POJO to store details of a food item.
 */
public class Food_data {
    private String Title, Disc, ImageID, Userid;

    /**
     * default constructor.
     */
    public Food_data() { }

    /**
     * Gets Disc property of the Food_data.
     * @return Disc property
     */
    public String getDisc() {
        return Disc;
    }

    /**
     * Sets Disc property of the Food_data.
     * @param disc value to set Disc to
     */
    public void setDisc(String disc) {
        Disc = disc;
    }

    /**
     * Gets the title of the Food_data
     * @return title of Food_data
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Sets the title of the Food_data.
     * @param title new title value
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Gets the ImageID of the Food_data.
     * @return food data image ID.
     */
    public String getImageID() {
        return ImageID;
    }

    /**
     * Sets the ImageID of the Food_data.
     * @param imageID new value for ImageId
     */
    public void setImageID(String imageID) {
        ImageID = imageID;
    }

    /**
     * Gets the ID of the User who owns the Food_data.
     * @return owner's userId
     */
    public String getUserid() {
        return Userid;
    }

    /**
     * Sets the ID of the User who owns the Food_data.
     * @param userid new value for userId
     */
    public void setUserid(String userid) {
        Userid = userid;
    }
}
