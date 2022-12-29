import firebase_admin
from firebase_admin import credentials, firestore, storage, db
import os
from sense_hat import SenseHat

cred=credentials.Certificate('./week10/serviceAccountKey.json')
firebase_admin.initialize_app(cred, {
    'storageBucket': 'plantio-898b2.appspot.com',
     'databaseURL' : 'https://plantio-898b2-default-rtdb.europe-west1.firebasedatabase.app/'
})



bucket = storage.bucket()
ref = db.reference('/')
home_ref = ref.child('file')
# data_ref = ref.child('data')

def store_file(fileLoc):

    filename=os.path.basename(fileLoc)

    # Store File in FB Bucket
    blob = bucket.blob(filename)
    outfile=fileLoc
    blob.upload_from_filename(outfile)

def push_db(fileLoc, time):
    filename = os.path.basename(fileLoc)

    home_ref.push(
        {
            'image':filename,
            'timestamp': time
        }
    )

# def push_db(humidity, pressure):
    
#     data_ref.push(
#         {
#             'humidity':humidity,
#             'pressure':pressure
#         }
#     )

if __name__ == "__main__":
    f = open("./test.txt", "w")
    f.write("a demo upload file to test Firebase Storage")
    f.close()
    store_file('./test.txt')
    push_db('./test.txt', '12/11/2020 9:00' )