package edu.swarthmore.cs.cs71.shelved.model.server;

import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;
import edu.swarthmore.cs.cs71.shelved.spark.PersistenceUtils;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class HibListService {
    public HibReadingList createList(String listName, boolean publicStatus) {
        HibReadingList newList = new HibReadingList();
        newList.resetName(listName);
        newList.setPublicStatus(publicStatus);
        PersistenceUtils.ENTITY_MANAGER.get().persist(newList);
        return newList;
    }

    public List<SimpleReadingList> getAllReadingLists(SessionFactory sf) {
        EntityManager session = sf.createEntityManager();
        List<SimpleReadingList> readingLists = new ArrayList<>();
        List<HibReadingList> hibReadingLists = session.createQuery("FROM HibReadingList").getResultList();
        for (HibReadingList list:hibReadingLists) {
            SimpleReadingList newSimpleReadingList = new SimpleReadingList("fix", false);
            newSimpleReadingList.resetName(list.getName());
            newSimpleReadingList.setPublicStatus(list.isPublicStatus());
            readingLists.add(newSimpleReadingList);
        }
        return readingLists;
    }
}
