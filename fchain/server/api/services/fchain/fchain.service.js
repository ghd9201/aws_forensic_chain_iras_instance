import l from '../../../common/logger';

const transaction = require('../../../blockchain/transaction/transaction');

class FchainService {
  getObject(req, res) {
    const args = [];

    args.push(req.params.id);
    return Promise.resolve(transaction.queryChainCode(null, 'fchannel', 'fchain',
      args, 'query', 'admin', 'Org1'));
  }

  delete(req, res) {
    const args = [];

    args.push(req.params.id);
    return Promise.resolve(transaction.queryChainCode(null, 'fchannel', 'fchain',
        args, 'delete', 'admin', 'Org1'));
  }

  addEvidenceRecord(req, res) {
    l.info(`${this.constructor.name}.byId(${req})`);
    const args = [];
    const peers = [];

    //args.push(req.body.balance.toString());

    args.push(req.body.objectId);
    args.push(req.body.timestamp);
    args.push(req.body.registerTime);
    args.push(req.body.caseId);
    args.push(req.body.evidenceId);
    args.push(req.body.fileName);
    args.push(req.body.fileSize);
    args.push(req.body.eventType);
    args.push(req.body.eventUserId);
    args.push(req.body.eventUserOrg);
    args.push(req.body.description);
    args.push(req.body.evidenceHash);


    peers.push('p0.org1.fchain.com');
    /*peers.push('p1.org1.fchain.com');
    peers.push('p0.org2.fchain.com');
    peers.push('p1.org2.fchain.com');
    peers.push('p0.org3.fchain.com');
    peers.push('p1.org3.fchain.com');*/


    l.debug(`invoke args:${args}`);

    l.debug(`invoke peers:${peers}`);
    return Promise.resolve(transaction.invokeChainCode(peers, 'fchannel', 'fchain',
      'addEvidenceRecord', args, 'admin', 'Org1'));
  }
}

export default new FchainService();
