package com.claricetechnologies.factory;

public class ClObjectFactory {
    private static ClObjectFactory mObjectFactory;

    private ClObjectFactory() {
        // Singleton implementation.
    }

    public static synchronized ClObjectFactory getInstance() {
        if (null == mObjectFactory) {
            synchronized (ClObjectFactory.class) {
                // Double checked singleton lazy initialization.
                mObjectFactory = new ClObjectFactory();
            }
        }
        return mObjectFactory;
    }
}
