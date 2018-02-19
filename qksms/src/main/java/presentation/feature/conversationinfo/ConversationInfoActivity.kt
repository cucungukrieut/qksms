/*
 * Copyright (C) 2017 Moez Bhatti <moez.bhatti@gmail.com>
 *
 * This file is part of QKSMS.
 *
 * QKSMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QKSMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QKSMS.  If not, see <http://www.gnu.org/licenses/>.
 */
package presentation.feature.conversationinfo

import android.os.Bundle
import com.jakewharton.rxbinding2.view.clicks
import com.moez.QKSMS.R
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable
import common.di.appComponent
import kotlinx.android.synthetic.main.conversation_info_activity.*
import presentation.common.base.QkThemedActivity

class ConversationInfoActivity : QkThemedActivity<ConversationInfoViewModel>(), ConversationInfoView {

    override val viewModelClass = ConversationInfoViewModel::class
    override val archiveIntent by lazy { archive.clicks() }
    override val deleteIntent by lazy { delete.clicks() }

    init {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation_info_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.bindView(this)
        setTitle(R.string.info_title)

        colors.background
                .autoDisposable(scope())
                .subscribe { color -> window.decorView.setBackgroundColor(color) }
    }

    override fun render(state: ConversationInfoState) {
        if (state.hasError) {
            finish()
            return
        }

        archive.title = getString(when(state.archived) {
            true -> R.string.info_unarchive
            false -> R.string.info_archive
        })
    }

}