package hr.fer.zemris.vhdllab.dao.impl;

import hr.fer.zemris.vhdllab.dao.EntityDao;
import hr.fer.zemris.vhdllab.dao.impl.support.AbstractDaoSupport;
import hr.fer.zemris.vhdllab.dao.impl.support.NamedEntityDao;
import hr.fer.zemris.vhdllab.dao.impl.support.NamedEntityTable;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.validator.InvalidStateException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Tests for {@link NamedEntityDao}.
 * 
 * @author Miro Bezjak
 * @version 1.0
 * @since vhdllab2
 */
public class NamedEntityDaoTest extends AbstractDaoSupport {

    @Resource(name = "namedEntityDao")
    private EntityDao<NamedEntityTable> dao;
    private NamedEntityTable entity;

    @Before
    public void initEachTest() {
        entity = new NamedEntityTable();
    }

    /**
     * Name can't be null.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void nameIsNull() {
        entity.setName(null);
        dao.persist(entity);
    }

    /**
     * Name can't be empty.
     */
    @Test(expected = InvalidStateException.class)
    public void nameIsEmpty() {
        entity.setName("");
        dao.persist(entity);
    }

    /**
     * Name is too long.
     */
    @Test(expected = InvalidStateException.class)
    public void nameTooLong() {
        entity.setName(RandomStringUtils.randomAlphabetic(256));
        dao.persist(entity);
    }

    /**
     * Name can't be updated.
     */
    @Test
    public void nameNotUpdateable() {
        entity.setName("entity_name");
        dao.persist(entity);
        String newName = "another_name";
        entity.setName(newName);
        dao.merge(entity);
        closeEntityManager(); // flush cache
        createEntityManager();
        NamedEntityTable loaded = (NamedEntityTable) entityManager.createQuery(
                "select e from NamedEntityTable e").getSingleResult();
        assertFalse(newName.equals(loaded.getName()));
    }

    /**
     * Name is not bound to name format constraint.
     */
    @Test
    public void nameNotWellFormed() {
        String name = "_illegal_name";
        entity.setName(name);
        dao.persist(entity); // no exception
        NamedEntityTable loaded = (NamedEntityTable) entityManager.createQuery(
                "select e from NamedEntityTable e").getSingleResult();
        assertTrue(name.equals(loaded.getName()));
    }

}
