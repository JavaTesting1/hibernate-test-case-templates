/**
 * Copyright (c) 2017-2019, Freistaat Bayern
 *
 * This software is unless otherwise specified property of the State of Bavaria.
 *
 * Unless required by applicable law or agreed to in writing, this software is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 */

package org.hibernate.bugs;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private Instant instant;

    public Long getId() {
        return this.id;
    }

    public Instant getInstant() {
        return this.instant;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setInstant(final Instant instant) {
        this.instant = instant;
    }

}
