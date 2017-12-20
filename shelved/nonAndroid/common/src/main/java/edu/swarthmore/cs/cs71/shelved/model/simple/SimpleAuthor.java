package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.Author;

public class SimpleAuthor implements Author {
    private String fullName;
    private String lastName;
    public String header = this.getClass().getSimpleName();

    public SimpleAuthor(String authorName) {
        this.fullName = authorName;
        this.lastName = authorName.substring(authorName.lastIndexOf(" ") +1);
    }

    @Override
    public String getAuthorName() {
        return fullName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (fullName != null ? !fullName.equals(author.getAuthorName()) : author.getAuthorName() != null) return false;
        return lastName != null ? lastName.equals(author.getLastName()) : author.getLastName() == null;
    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
