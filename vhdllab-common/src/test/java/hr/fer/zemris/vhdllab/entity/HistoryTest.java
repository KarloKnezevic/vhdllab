/*******************************************************************************
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package hr.fer.zemris.vhdllab.entity;

import static hr.fer.zemris.vhdllab.entity.stub.HistoryStub.CREATED_ON;
import static hr.fer.zemris.vhdllab.entity.stub.HistoryStub.DELETED_ON;
import static hr.fer.zemris.vhdllab.entity.stub.HistoryStub.INSERT_VERSION;
import static hr.fer.zemris.vhdllab.entity.stub.HistoryStub.INSERT_VERSION_2;
import static hr.fer.zemris.vhdllab.entity.stub.HistoryStub.UPDATE_VERSION;
import static hr.fer.zemris.vhdllab.entity.stub.HistoryStub.UPDATE_VERSION_2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import hr.fer.zemris.vhdllab.entity.stub.HistoryStub;
import hr.fer.zemris.vhdllab.test.ValueObjectTestSupport;

import org.junit.Before;
import org.junit.Test;

public class HistoryTest extends ValueObjectTestSupport {

    private History entity;

    @Before
    public void initEntity() {
        entity = new HistoryStub();
    }

    @Test
    public void basics() {
        History another = new History();
        assertEquals("insert version not 0.", Integer.valueOf(0), another
                .getInsertVersion());
        assertEquals("update version not 0.", Integer.valueOf(0), another
                .getUpdateVersion());
        assertNotNull("created on is null.", another.getCreatedOn());
        assertNull("deleted on is set.", another.getDeletedOn());

        another.setInsertVersion(INSERT_VERSION);
        assertNotNull("insert version is null.", another.getInsertVersion());
        another.setInsertVersion(null);
        assertNull("insert version not cleared.", another.getInsertVersion());

        another.setUpdateVersion(UPDATE_VERSION);
        assertNotNull("update version is null.", another.getUpdateVersion());
        another.setUpdateVersion(null);
        assertNull("update version not cleared.", another.getUpdateVersion());

        another.setCreatedOn(CREATED_ON);
        assertNotNull("created on is null.", another.getCreatedOn());
        another.setCreatedOn(null);
        assertNull("created on not cleared.", another.getCreatedOn());

        another.setDeletedOn(DELETED_ON);
        assertNotNull("deleted on is null.", another.getDeletedOn());
        another.setDeletedOn(null);
        assertNull("deleted on not cleared.", another.getDeletedOn());
    }

    @Test
    public void constructor() {
        History another = new History(INSERT_VERSION, UPDATE_VERSION);
        assertEquals("insert version not set.", INSERT_VERSION, another
                .getInsertVersion());
        assertEquals("update version not set.", UPDATE_VERSION, another
                .getUpdateVersion());
        assertNotNull("created on is null.", another.getCreatedOn());
        assertNull("deleted on is set.", another.getDeletedOn());
    }

    @Test
    public void copyConstructor() {
        History another = new History(entity);
        assertEquals("insert version not same.", entity.getInsertVersion(),
                another.getInsertVersion());
        assertEquals("update version not same.", entity.getUpdateVersion(),
                another.getUpdateVersion());
        assertEquals("created on not same.", entity.getCreatedOn(), another
                .getCreatedOn());
        assertEquals("deleted on not same.", entity.getDeletedOn(), another
                .getDeletedOn());
    }

    @Test(expected = NullPointerException.class)
    public void copyConstructorNullArgument() {
        new History(null);
    }

    @Test
    public void hashCodeAndEquals() {
        basicEqualsTest(entity);

        History another = new History(entity);
        assertEqualsAndHashCode(entity, another);

        another.setInsertVersion(INSERT_VERSION_2);
        assertNotEqualsAndHashCode(entity, another);

        another = new History(entity);
        another.setUpdateVersion(UPDATE_VERSION_2);
        assertNotEqualsAndHashCode(entity, another);
    }

    @Test
    public void testToString() {
        toStringPrint(entity);
    }

}
