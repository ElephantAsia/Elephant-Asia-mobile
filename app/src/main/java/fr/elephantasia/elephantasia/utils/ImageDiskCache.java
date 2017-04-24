package fr.elephantasia.elephantasia.utils;

/*public class ImageDiskCache {

    public final static String CACHE_TAG = "imagediskcache";
    //private static final Map<ImageElement, WebTask> WEBTASKS = new HashMap<>();
    private static final Map<ImageElement, DiskTask> DISKTASKS = new HashMap<>();
    private static final Map<ImageElement, Set<Listener>> LISTENERS = new HashMap<>();

    public void load(
            @NonNull final Context context,
            @NonNull ImageElement element,
            @NonNull Listener listener
    ) {
        Bitmap bitmap = null;

        bitmap = BitmapCache.getInCache(CACHE_TAG + element.name + element.size);
        if (bitmap != null && !bitmap.isRecycled()) {
            listener.onResult(element, bitmap, context);
            return;
        }

        Set<Listener> listeners = LISTENERS.get(element);
        if (listeners == null) {
            listeners = new HashSet<>();
        }
        listeners.add(listener);
        LISTENERS.put(element, listeners);
        WebTask webtask = WEBTASKS.get(element);
        if (webtask != null) {
            return;
        }
        DiskTask disktask = DISKTASKS.get(element);
        if (disktask != null) {
            return;
        }
        File file = element.getFile(context);
        if (file != null && file.exists()) {
            disktask = new DiskTask(
                    context,
                    element,
                    new Listener() {
                        @Override
                        public void onResult(
                                @NonNull ImageElement element,
                                @Nullable Bitmap bitmap,
                                @NonNull Context context
                        ) {
                            notifyResult(element, bitmap, context, nbPhoto, enableCache);
                        }
                        @Override
                        public void onProgress(@NonNull ImageElement element, @NonNull Context context, @Nullable Integer progress) {

                        }
                    }
            );
            DISKTASKS.put(element, disktask);
            disktask.execute();
        } else {
            webtask = new WebTask(
                    context,
                    element,
                    index,
                    new Listener() {
                        @Override
                        public void onResult(
                                @NonNull ImageElement element,
                                @Nullable Bitmap bitmap,
                                @NonNull Context context,
                                @NonNull int nbPhoto
                        ) {
                            notifyResult(element, bitmap, context, nbPhoto, enableCache);
                        }

                        @Override
                        public void onProgress(@NonNull ImageElement element, @NonNull Context context, @Nullable Integer progress) {
                            Set<Listener> listeners = LISTENERS.get(element);
                            if (listeners != null) {
                                for (Listener listener : listeners) {
                                    listener.onProgress(element, context, progress);
                                }
                            }
                        }
                    }
            );
            WEBTASKS.put(element, webtask);
            webtask.execute();
        }
    }

    public static void putInCache(
            @NonNull ImageElement element,
            @Nullable Bitmap bitmap,
            boolean enableCache
    ) {
        if (bitmap != null && enableCache) {
            BitmapCache.putInCache(CACHE_TAG + element.name + element.size, bitmap);
        }
    }

    private static void notifyResult(
            @NonNull ImageElement element,
            @Nullable Bitmap bitmap,
            @NonNull Context context,
            int nbPhoto,
            boolean enableCache
    ) {
        //synchronized (CACHE) {
            if (bitmap != null && enableCache) {
                //CACHE.put(element, bitmap);
                BitmapCache.putInCache(CACHE_TAG + element.name + element.size, bitmap);
            }
            WebTask webtask = WEBTASKS.remove(element);
            if (webtask != null) {
                webtask.cancel(true);
            }
            DiskTask disktask = DISKTASKS.remove(element);
            if (disktask != null) {
                disktask.cancel(true);
            }
            Set<Listener> listeners = LISTENERS.remove(element);
            if (listeners != null) {
                for (Listener listener : listeners) {
                    listener.onResult(element, bitmap, context, nbPhoto);
                }
            }
        //}
    }

    public static void cancelLoad(ImageElement element, Context context) {
        ImageDiskCache.WebTask webtask = WEBTASKS.remove(element);
        if (webtask != null) {
            webtask.cancel(true);
            LISTENERS.remove(element);
        }
    }

    public static void cancel(
            @NonNull ImageElement element,
            @NonNull Context context
    ) {
        notifyResult(element, null, context, 0, true);
    }

    public interface Listener {

        void onResult(
                @NonNull ImageElement element,
                @Nullable Bitmap bitmap,
                @NonNull Context context,
                @NonNull int size
        );

        void onProgress(
                @NonNull ImageElement element,
                @NonNull Context context,
                @Nullable Integer progress
        );

    }
    private static class DiskTask extends AsyncTask<Void, Void, Void> {

        @NonNull
        private final Context context;

        @NonNull
        private final ImageElement element;

        @NonNull
        private final Listener listener;

        @Nullable
        private Bitmap bitmap;

        private int nbPhoto;

        public DiskTask(
                @NonNull Context context,
                @NonNull ImageElement element,
                @NonNull Listener listener
        ) {
            this.context = context;
            this.element = element;
            this.listener = listener;
        }

        public void execute() {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        @Override
        protected Void doInBackground(@Nullable Void... params) {
            File file = element.getFile(context);
            if (file != null) {
                String path = file.getAbsolutePath();
                bitmap = BitmapFactory.decodeFile(path);
                //BitmapCache.putInCache(CACHE_TAG + );
            }
            return null;
        }

        @Override
        protected void onPostExecute(@Nullable Void result) {
            listener.onResult(element, bitmap, context, nbPhoto);
        }
    }

}*/
