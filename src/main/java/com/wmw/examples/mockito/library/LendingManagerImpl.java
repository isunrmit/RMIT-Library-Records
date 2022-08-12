/**
 * @author Wei-Ming Wu
 * <p>
 * <p>
 * Copyright 2014 Wei-Ming Wu
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.wmw.examples.mockito.library;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class LendingManagerImpl implements LendingManager {

    @Inject
    LibraryRecordDAO libraryRecordDAO;

    public void setLibraryRecordDAO(LibraryRecordDAO dao) {
        libraryRecordDAO = dao;
    }

    @Override
    public LibraryRecord borrowBook(Book book) {
        List<LibraryRecord> records =
                checkRecordIntegrity(libraryRecordDAO.findByBook(book));
        for (LibraryRecord record : records) {
            if (record.getReturningDate() == null)
                throw new IllegalStateException("This book is not returned yet.");
        }

        LibraryRecord record = new LibraryRecord();
        record.setBook(book);
        record.setBorrowingDate(new Date());
        if (!libraryRecordDAO.save(record))
            throw new IllegalStateException("New library record can't be saved.");
        return record;
    }

    @Override
    public LibraryRecord returnBook(Book book) {
        List<LibraryRecord> records =
                checkRecordIntegrity(libraryRecordDAO.findByBook(book));
        for (LibraryRecord record : records) {
            if (record.getReturningDate() == null) {
                record.setReturningDate(new Date());
                if (!libraryRecordDAO.save(record))
                    throw new IllegalStateException("Library record can't be updated.");
                return record;
            }
        }
        throw new IllegalStateException("This book is not borrowed.");
    }

    private List<LibraryRecord> checkRecordIntegrity(List<LibraryRecord> records) {
        int emptyReturningDates = 0;
        for (LibraryRecord record : records) {
            if (record.getBorrowingDate() == null)
                throw new IllegalStateException("Empty borrowing date is found.");

            if (record.getReturningDate() == null)
                emptyReturningDates++;
        }

        if (emptyReturningDates > 1)
            throw new IllegalStateException("Multiple returning dates are empty.");

        return records;
    }

}
