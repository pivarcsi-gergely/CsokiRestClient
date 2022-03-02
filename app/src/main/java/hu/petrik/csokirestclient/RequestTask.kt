package hu.petrik.csokirestclient

import android.os.AsyncTask
import java.io.IOException

class RequestTask: AsyncTask<Void?, Void?, Response?> {
    var lastTask:(()->Unit)?

    constructor(lastTask:(()->Unit)?) {
        this.lastTask = lastTask
    }

    protected override fun doInBackground(vararg p0: Void?): Response {
        var response = Response(0, "")

        try {
            response = RequestHandler.get("https://retoolapi.dev/GIl55L/Csokoladek")!!
        }
        catch (e: IOException) {

        }
        return response
    }

    override fun onPostExecute(result: Response?) {
        super.onPostExecute(result)

    }
}