import * as express from 'express';
import controller from './controller';

export default express
  .Router()
  .get('/getobject', controller.getObject)
  .get('/delete', controller.delete)
  .post('/addEvidenceRecord', controller.addEvidenceRecord)
