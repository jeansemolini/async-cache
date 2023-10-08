package com.semolini.async.cache;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import com.semolini.async.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class UserSerializer implements StreamSerializer<CompletableFuture<User>> {
    @Override
    public void write(ObjectDataOutput out, CompletableFuture<User> userCompletableFuture) throws IOException {
        try {
            out.writeString(userCompletableFuture.get().getName());
            out.writeString(userCompletableFuture.get().getBlog());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<User> read(ObjectDataInput in) throws IOException {
        User user = new User();
        user.setName(in.readString());
        user.setBlog(in.readString());
        return CompletableFuture.completedFuture(user);
    }

    @Override
    public int getTypeId() {
        return 1;
    }
}
