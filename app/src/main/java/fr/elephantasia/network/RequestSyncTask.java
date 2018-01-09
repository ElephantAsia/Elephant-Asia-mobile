package fr.elephantasia.network;

/**
 * Created by Stephane on 08/01/2018.
 */

public abstract class RequestSyncTask<ReturnType> extends Request {

	public abstract ReturnType execute();

}
