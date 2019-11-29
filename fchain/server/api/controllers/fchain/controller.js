import FchainService from '../../services/fchain/fchain.service';

export class Controller {
  getObject(req, res) {
    FchainService
      .getObject(req, res)
      .then(r => {
        if (r) res.json(r);
        else res.status(404).end();
      });
  }

  delete(req, res) {
    FchainService
      .delete(req, res)
      .then(r => {
        if (r) res.json(r);
        else res.status(404).end();
      });
  }

  addEvidenceRecord(req, res) {
    FchainService
      .addEvidenceRecord(req, res)
      .then(r => {
        if (r) res.json(r);
        else res.status(404).end();
      });
  }
}
export default new Controller();
