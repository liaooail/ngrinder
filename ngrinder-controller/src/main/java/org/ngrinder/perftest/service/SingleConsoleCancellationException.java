/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.ngrinder.perftest.service;

import org.ngrinder.common.exception.NGrinderRuntimeException;

/**
 * Exception which occurs when on-going PerfTest cancellation is detected.
 * 
 * @author JunHo Yoon
 * @since 3.0
 */
public class SingleConsoleCancellationException extends NGrinderRuntimeException {

	/** UUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public SingleConsoleCancellationException() {
		super("");
	}

	/**
	 * Constructor.
	 * 
	 * @param message	message
	 */
	public SingleConsoleCancellationException(String message) {
		super(message);
	}

}
