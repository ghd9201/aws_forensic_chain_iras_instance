import examplesRouter from './api/controllers/examples/router';
import fchainRouter from './api/controllers/fchain/router';

export default function routes(app) {
  app.use('/api/v1/examples', examplesRouter);
  app.use('/api/v1/fchain', fchainRouter);
	
}
