package fr.elephantasia.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.elephantasia.R;


public class ImageUtil {

  private static final String TEMP_FOLDER = "temp";
  private static final String ORIGIN_FOLDER = "original";
	private static final String THUMB_FOLDER = "thumb";

  private final static String PHOTO_PREFIX = "photo";
  private final static String PHOTO_SUFFIX = ".jpg";

  private static final List<String> ACCEPTED_EXTENSIONS = new ArrayList<>(
      Arrays.asList("jpg", "png", "jpeg")
  );

  private static void rotateImage(String photo) {
    ExifInterface exif;
    try {
      exif = new ExifInterface(photo);

      int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
      Matrix matrix = new Matrix();
      if (orientation == 6) {
        matrix.postRotate(90);
      } else if (orientation == 3) {
        matrix.postRotate(180);
      } else if (orientation == 8) {
        matrix.postRotate(270);
      }
      if (orientation != 0) {
        File file = new File(photo);
        Bitmap bitmap = BitmapFactory.decodeFile(photo);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        FileOutputStream out = new FileOutputStream(file);
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        scaledBitmap.recycle();
        bitmap.recycle();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /* public static File getCapturePhotoFile(Context context) {
		File dir = getOriginalPhotoDir(context);
		if (dir != null) {
			dir.mkdirs();
			return new File(dir, PHOTO_PREFIX + PHOTO_SUFFIX);
		}
		return null;
	} */

	@Nullable
	private static File getOriginalPhotoDir(Context context) {
		File dir = context.getExternalCacheDir();
		if (dir != null) {
			return new File(dir.getAbsolutePath() + File.separator + ORIGIN_FOLDER);
		}
		return null;
	}

	public static File getCapturePhotoFile(Context context) {
    File dir = getTempPhotoDir(context);
    if (dir != null) {
      dir.mkdirs();
      return new File(dir, PHOTO_PREFIX + PHOTO_SUFFIX);
    }
    return null;
  }

   @Nullable
  	private static File getTempPhotoDir(Context context) {
    File dir = context.getExternalCacheDir();
    if (dir != null) {
      String path = dir.getAbsolutePath();
      path += File.separator + TEMP_FOLDER;
      return new File(path);
    }
    return null;
  }

  /* private static File createTempPhotoFile(Context context) {
    File dir = getTempPhotoDir(context);
    if (dir != null) {
      dir.mkdirs();
      try {
        return File.createTempFile(PHOTO_PREFIX, PHOTO_SUFFIX, dir);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  } */

  private static File createOriginalFile(Context context) {
		// File dir = getTempPhotoDir(context);
		File originalDir = getOriginalPhotoDir(context);
		if (originalDir != null) {
			originalDir.mkdirs();
			try {
				return File.createTempFile(PHOTO_PREFIX, PHOTO_SUFFIX, originalDir);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

  @Nullable
  public static String createImageFileFromUri(Context context, Uri uri) {
    String extension = FilenameUtils.getExtension(uri.toString());
    boolean isInGalerie = !uri.toString().contains(".");
    InputStream input = null;
    File file = null;

    try {
      input = context.getContentResolver().openInputStream(uri);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (input == null) {
      Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show();
      return null;
    }

    if (isInGalerie || ACCEPTED_EXTENSIONS.contains(extension)) {
			file = createOriginalFile(context);
    }

    if (file == null) {
      return null;
    }

    boolean copied = StaticTools.copyStreamToFile(input, file);
    if (!copied) {
      return null;
    }

    File temp = getCapturePhotoFile(context);
		if (temp != null) {
			temp.delete();
		}

    String path = file.getAbsolutePath();
    rotateImage(path);
    return path;
  }

  static public byte[] resizeImage(String path, float maxW, float maxH) {

    File file = new File(path);

    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

    float height = (float) bitmap.getHeight();
    float width = (float) bitmap.getWidth();
    float ratio = height / width;

    if (ratio > 1) {
      if (bitmap.getHeight() > maxH) {
        height = maxH;
        width = maxH / ratio;
      }
    } else {
      if (bitmap.getWidth() > maxW) {
        width = maxW;
        height = maxW * ratio;
      }
    }
    bitmap = bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
    byte[] byteArray = stream.toByteArray();
    bitmap.recycle();

    return byteArray;
  }

}
